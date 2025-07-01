package com.things.cgomp.order.service.impl.order.impl;

import com.things.cgomp.common.core.enums.EnableEnum;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.core.utils.DateUtils;
import com.things.cgomp.common.core.utils.MathUtil;
import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.device.enums.YkcDeviceChargeStopReasonEnum;
import com.things.cgomp.common.mq.message.DrawGunReqMsg;
import com.things.cgomp.common.mq.message.TradingRecordConfirmReqMsg;
import com.things.cgomp.common.mq.message.order.EndChargingReqMsg;
import com.things.cgomp.order.api.dto.AddOrderDTO;
import com.things.cgomp.order.api.enums.OrderSnPrefixEnum;
import com.things.cgomp.order.api.enums.OrderStateEnum;
import com.things.cgomp.order.domain.ChargeTradeDetail;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.enums.*;
import com.things.cgomp.order.service.IChargeTradeDetailService;
import com.things.cgomp.order.service.IOrderInfoService;
import com.things.cgomp.order.service.impl.order.OrderState;
import com.things.cgomp.device.api.dto.RuleDTO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Component
public class EndChargingOrderState extends OrderState {

    @Resource
    private IChargeTradeDetailService chargeTradeDetailService;

    @Override
    public Long addOrder(
            AddOrderDTO order
    ) {
        return null;
    }

    @Override
    public void endCharging(
            OrderInfo orderInfo,
            EndChargingReqMsg reqMsg
    ) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderInfo confirmTradingRecord(
            OrderInfo orderInfo,
            TradingRecordConfirmReqMsg reqMsg
    ) {

        return saveOrderData(
                orderInfo,
                reqMsg
        );
    }

    private OrderInfo saveOrderData(
            OrderInfo orderInfo,
            TradingRecordConfirmReqMsg reqMsg
    ) {
        if (orderInfo != null) {
            return updateOrderData(
                    orderInfo,
                    reqMsg
            );
        }

        return addOrderData(reqMsg);
    }

    @Override
    public void drawGun(
            OrderInfo orderInfo,
            DrawGunReqMsg reqMsg
    ) {

    }

    private OrderInfo addOrderData(
            TradingRecordConfirmReqMsg reqMsg
    ) {
        DeviceInfo pile = getDeviceInfo(reqMsg.getPileId());
        RuleDTO ruleDTO = getRuleDTO(pile);
        List<ChargeTradeDetail> chargeTradeDetails = chargeTradeDetailService.getChargeTradeDetails(
                reqMsg,
                ruleDTO
        );

        OrderInfo orderInfo = addOrder(
                pile,
                reqMsg,
                chargeTradeDetails
        );

        chargeTradeDetailService.saveChargeTradeDetails(
                orderInfo.getId(),
                chargeTradeDetails
        );

        orderLogService.saveLog(
                orderInfo.getId(),
                orderInfo.getSettlementTime(),
                OrderLogEnum.TRADING_RECORD_CONFIRM
        );

        return orderInfo;
    }

    private OrderInfo addOrder(
            DeviceInfo pile,
            TradingRecordConfirmReqMsg reqMsg,
            List<ChargeTradeDetail> chargeTradeDetails
    ) {
        IOrderInfoService orderInfoService = getOrderInfoService();
        Long userId = orderInfoService.selectLatestUserId(reqMsg.getVin());

        BigDecimal chargeFee = getChargeFee(chargeTradeDetails);

        Integer endReasonCode = convertEndReasonCode(reqMsg.getEndReasonCode());
        Integer abnormalStatus = getAbnormalStatus(endReasonCode);

        OrderInfo orderInfo = new OrderInfo()
                .setSn(OrderSnPrefixEnum.CHARGING.getPrefix() + reqMsg.getOrderNo())
                .setTradeSn(reqMsg.getOrderNo())
                .setCardNo(reqMsg.getCardNo())
                .setPileId(reqMsg.getPileId())
                .setPortId(reqMsg.getPortId())
                .setRealStartTime(DateUtils.toLocalDateTime(reqMsg.getStartTime()))
                .setRealEndTime(DateUtils.toLocalDateTime(reqMsg.getEndTime()))
                .setConsumeElectricity(reqMsg.getElectricity())
                .setEndReasonCode(endReasonCode)
                .setEndReasonDesc(reqMsg.getEndReasonDesc())
                .setChargeFee(chargeFee)
                .setOrderAmount(reqMsg.getAmount())
                .setVin(reqMsg.getVin())
                .setSettlementTime(DateUtils.toLocalDateTime(reqMsg.getOrderTime()))
                .setProcessLoss(EnableEnum.ENABLE.getCode())
                .setProcessStep(ProcessStepEnum.UNDIVIDED_COMMISSION.getType())
                .setAbnormalStatus(abnormalStatus)
                .setUserId(userId)
                .setOrderState(OrderStateEnum.TRADING_RECORD_CONFIRM.getType());

        if(pile != null){
            orderInfo.setSiteId(pile.getSiteId())
                    .setIsFee(pile.getIsFree())
                    .setPayRuleId(pile.getCurrentPayRuleId())
                    .setPayModelId(pile.getCurrentPayModelId());
        }

        BigDecimal payAmount = getPayAmount(
                orderInfo,
                reqMsg.getAmount()
        );

        orderInfo.setPayAmount(payAmount);

        orderInfo.setServiceFee()
                .setRealChargingTime();

        orderInfoService.save(orderInfo);
        return orderInfoService.getById(orderInfo.getId());
    }

    private Integer convertEndReasonCode(Integer endReasonCode) {
        if (YkcDeviceChargeStopReasonEnum.isAbnormal(endReasonCode)) {
            return EndReasonEnum.CHARGING_PILE_ABNORMAL_STOP.getType();
        }

        if (YkcDeviceChargeStopReasonEnum.APP_REMOTE_CLOSE.getReason().equals(endReasonCode)
                || YkcDeviceChargeStopReasonEnum.MANUALLY_STOP.getReason().equals(endReasonCode)
        ) {
            return EndReasonEnum.USER_ACTIVE_STOP.getType();
        }

        if (YkcDeviceChargeStopReasonEnum.BALANCE_INSUFFICIENT.getReason().equals(endReasonCode)
                || YkcDeviceChargeStopReasonEnum.MONEY_STOP.getReason().equals(endReasonCode)
                || YkcDeviceChargeStopReasonEnum.TIME_STOP.getReason().equals(endReasonCode)
        ) {
            return EndReasonEnum.MEET_SET_CONDITIONS_STOP_CHARGING.getType();
        }

        if (YkcDeviceChargeStopReasonEnum.SOC100_CLOSE.getReason().equals(endReasonCode)) {
            return EndReasonEnum.CHARGING_PILE_FULL_ACTIVE_STOP.getType();
        }

        return endReasonCode;
    }

    private RuleDTO getRuleDTO(DeviceInfo deviceInfo) {
        if(deviceInfo == null){
            return null;
        }
       return getRuleDTO(
               deviceInfo.getPayRuleId(),
               deviceInfo.getPayModelId()
       );
    }

    private OrderInfo updateOrderData(
            OrderInfo orderInfo,
            TradingRecordConfirmReqMsg reqMsg
    ) {
        RuleDTO ruleDTO = getRuleDTO(
                orderInfo.getPayRuleId(),
                orderInfo.getPayModelId()
        );
        List<ChargeTradeDetail> chargeTradeDetails = chargeTradeDetailService
                .getChargeTradeDetails(
                        reqMsg,
                        ruleDTO
                );

        OrderInfo updatedOrder = updateOrder(
                orderInfo,
                reqMsg,
                chargeTradeDetails
        );

        chargeTradeDetailService.saveChargeTradeDetails(
                updatedOrder.getId(),
                chargeTradeDetails
        );

        orderLogService.saveLog(
                updatedOrder.getId(),
                updatedOrder.getSettlementTime(),
                OrderLogEnum.TRADING_RECORD_CONFIRM
        );
        return updatedOrder;
    }

    private OrderInfo updateOrder(
            OrderInfo orderInfo,
            TradingRecordConfirmReqMsg reqMsg,
            List<ChargeTradeDetail> chargeTradeDetails
    ) {
        BigDecimal chargeFee = getChargeFee(chargeTradeDetails);
        BigDecimal payAmount = getPayAmount(
                orderInfo,
                reqMsg.getAmount()
        );

        Integer endReasonCode = convertEndReasonCode(reqMsg.getEndReasonCode());
        Integer abnormalStatus = getAbnormalStatus(endReasonCode);

        OrderInfo updateOrder = new OrderInfo()
                .setId(orderInfo.getId())
                .setVersion(orderInfo.getVersion())
                .setCardNo(reqMsg.getCardNo())
                .setRealStartTime(DateUtils.toLocalDateTime(reqMsg.getStartTime()))
                .setRealEndTime(DateUtils.toLocalDateTime(reqMsg.getEndTime()))
                .setConsumeElectricity(reqMsg.getElectricity())
                .setEndReasonCode(endReasonCode)
                .setEndReasonDesc(reqMsg.getEndReasonDesc())
                .setChargeFee(chargeFee)
                .setOrderAmount(reqMsg.getAmount())
                .setPayAmount(payAmount)
                .setProcessStep(ProcessStepEnum.UNDIVIDED_COMMISSION.getType())
                .setSettlementTime(DateUtils.toLocalDateTime(reqMsg.getOrderTime()))
                .setAbnormalStatus(abnormalStatus)
                .setOrderState(OrderStateEnum.TRADING_RECORD_CONFIRM.getType());
        updateOrder.setServiceFee()
                .setRealChargingTime();

        IOrderInfoService orderInfoService = getOrderInfoService();
        boolean success = orderInfoService.updateById(updateOrder);
        if(!success){
            throw new ServiceException(ErrorCodeConstants.VERSION_NUMBER_CONFLICT);
        }

        return orderInfoService.getById(orderInfo.getId());
    }

    private Integer getAbnormalStatus(Integer endReasonCode) {
        if (EndReasonEnum.CHARGING_PILE_ABNORMAL_STOP.getType().equals(endReasonCode)) {
            return AbnormalStatusEnum.ABNORMAL.getType();
        }

        return AbnormalStatusEnum.NORMAL.getType();
    }

    private BigDecimal getChargeFee(
            List<ChargeTradeDetail> chargeTradeDetails
    ) {
        double chargeFee = chargeTradeDetails.stream()
                .map(ChargeTradeDetail::buildChargeFee)
                .mapToDouble(BigDecimal::floatValue)
                .sum();

        return MathUtil.getTwoDecimal(chargeFee);
    }
}
