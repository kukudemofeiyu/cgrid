package com.things.cgomp.order.service.impl.order.impl;

import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.core.utils.DateUtils;
import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.mq.message.DrawGunReqMsg;
import com.things.cgomp.common.mq.message.TradingRecordConfirmReqMsg;
import com.things.cgomp.common.mq.message.order.EndChargingReqMsg;
import com.things.cgomp.order.api.constants.OrderRedisConstants;
import com.things.cgomp.order.api.dto.AddOrderDTO;
import com.things.cgomp.order.api.enums.OccupyTypeEnum;
import com.things.cgomp.order.api.enums.OrderSnPrefixEnum;
import com.things.cgomp.order.api.enums.OrderStateEnum;
import com.things.cgomp.order.api.enums.OrderTypeEnum;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.enums.*;
import com.things.cgomp.order.service.IOrderInfoService;
import com.things.cgomp.order.service.impl.order.OrderState;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class InsertGunOrderState extends OrderState {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addOrder(
            AddOrderDTO order
    ) {
        LocalDateTime insertTime = order.buildInsertTime();

        IOrderInfoService orderInfoService = getOrderInfoService();
        OrderInfo orderInfo = orderInfoService.selectOrderBySn(OrderSnPrefixEnum.CHARGING.getPrefix() + order.getSn());
        if(orderInfo != null){
            throw new ServiceException(ErrorCodeConstants.ORDER_NUMBER_ALREADY_EXISTS);
        }

        DeviceInfo portDevice = getDeviceInfo(order.getPortId());
        if(portDevice == null){
            throw new ServiceException(ErrorCodeConstants.DEVICE_DOES_NOT_EXIST);
        }

        DeviceInfo pileDevice = getDeviceInfo(portDevice.getParentId());
        if(pileDevice == null){
            throw new ServiceException(ErrorCodeConstants.DEVICE_DOES_NOT_EXIST);
        }

        Long currentPayRuleId = pileDevice.getCurrentPayRuleId();
        Integer currentPayModelId = pileDevice.getCurrentPayModelId();
        if(currentPayRuleId == null || currentPayModelId == null){
            throw new ServiceException(ErrorCodeConstants.RULE_NOT_DELIVERED_TO_DEVICE);
        }

        OrderInfo insertOrder = new OrderInfo();
        LocalDateTime realStartTime = DateUtils.toLocalDateTime(order.getStartTime());
        insertOrder.setSn(OrderSnPrefixEnum.CHARGING.getPrefix() + order.getSn())
                .setTradeSn(order.getSn())
                .setPayType(order.getPayType())
                .setOrderType(order.getOrderType())
                .setOrderSource(order.getOrderSource())
                .setBillType(order.getBillType())
                .setPileId(pileDevice.getDeviceId())
                .setPortId(portDevice.getDeviceId())
                .setRealStartTime(realStartTime)
                .setIsFee(pileDevice.getIsFree())
                .setDeviceType(order.getDeviceType())
                .setPayRuleId(currentPayRuleId)
                .setPayModelId(currentPayModelId)
                .setUserId(order.getUserId())
                .setPhone(order.getPhone())
                .setLicensePlateNumber(order.getLicensePlateNumber())
                .setVin(order.getVin())
                .setSiteId(pileDevice.getSiteId())
                .setInsertTime(insertTime)
                .setCreateTime(insertTime)
                .setOrderState(OrderStateEnum.CHARGING.getType());

        orderInfoService.save(insertOrder);

        orderLogService.saveLog(
                insertOrder.getId(),
                insertTime,
                OrderLogEnum.INSERT_GUN
        );

        orderLogService.saveLog(
                insertOrder.getId(),
                realStartTime,
                OrderLogEnum.CHARGING
        );

        redisService.deleteObject(OrderRedisConstants.ORDER_SN_KEY+order.getPortId());

        BigDecimal occupyFee = siteOccupyFeeService.calculateFee(
                pileDevice.getSiteId(),
                OccupyTypeEnum.BEFORE_CHARGING.getType(),
                insertTime,
                realStartTime
        );
        if(occupyFee != null){
            addOccupy(
                    insertOrder,
                    pileDevice,
                    portDevice,
                    occupyFee
            );
        }

        return insertOrder.getId();
    }

    private OrderInfo addOccupy(
            OrderInfo parentOrder,
            DeviceInfo pileDevice,
            DeviceInfo portDevice,
            BigDecimal occupyFee
    ) {
        OrderInfo insertOrder = new OrderInfo();
        insertOrder.setParentId(parentOrder.getId())
                .setSn(OrderSnPrefixEnum.OCCUPY_BEFORE_CHARGING.getPrefix() + parentOrder.getTradeSn())
                .setTradeSn(parentOrder.getTradeSn())
                .setOrderType(OrderTypeEnum.OCCUPY.getType())
                .setOccupyType(OccupyTypeEnum.BEFORE_CHARGING.getType())
                .setOrderSource(parentOrder.getOrderSource())
                .setPileId(pileDevice.getDeviceId())
                .setPortId(portDevice.getDeviceId())
                .setRealStartTime(parentOrder.getInsertTime())
                .setRealEndTime(parentOrder.getRealStartTime())
                .setIsFee(pileDevice.getIsFree())
                .setDeviceType(parentOrder.getDeviceType())
                .setUserId(parentOrder.getUserId())
                .setPhone(parentOrder.getPhone())
                .setLicensePlateNumber(parentOrder.getLicensePlateNumber())
                .setVin(parentOrder.getVin())
                .setSiteId(pileDevice.getSiteId())
                .setInsertTime(parentOrder.getInsertTime())
                .setCreateTime(parentOrder.getInsertTime())
                .setOrderAmount(occupyFee)
                .setEndReasonCode(EndReasonEnum.START_CHARGING.getType())
                .setEndReasonDesc(EndReasonEnum.START_CHARGING.getDescription())
                .setOrderState(OrderStateEnum.GUN_DRAWN.getType())
                .setPayType(parentOrder.getPayType());
        insertOrder.setRealChargingTime();

        BigDecimal payAmount = getPayAmount(
                insertOrder,
                occupyFee
        );

        insertOrder.setPayAmount(payAmount);

        IOrderInfoService orderInfoService = getOrderInfoService();
        orderInfoService.save(insertOrder);

        orderLogService.saveLog(
                insertOrder.getId(),
                parentOrder.getInsertTime(),
                OrderLogEnum.INSERT_GUN
        );

        orderLogService.saveLog(
                insertOrder.getId(),
                parentOrder.getRealStartTime(),
                OrderLogEnum.CHARGING
        );

        return orderInfoService.getById(insertOrder.getId());
    }

    @Override
    public void endCharging(
            OrderInfo orderInfo,
            EndChargingReqMsg reqMsg
    ) {
    }

    @Override
    public OrderInfo confirmTradingRecord(
            OrderInfo orderInfo,
            TradingRecordConfirmReqMsg reqMsg
    ) {
        return null;
    }

    @Override
    public void drawGun(
            OrderInfo orderInfo,
            DrawGunReqMsg reqMsg
    ) {

    }
}
