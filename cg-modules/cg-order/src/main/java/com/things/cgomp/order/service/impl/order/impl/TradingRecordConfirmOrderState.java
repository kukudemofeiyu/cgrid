package com.things.cgomp.order.service.impl.order.impl;

import com.things.cgomp.app.api.domain.AppUser;
import com.things.cgomp.app.api.domain.AppUserCar;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.enums.EnableEnum;
import com.things.cgomp.common.core.utils.DateUtils;
import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.mq.message.DrawGunReqMsg;
import com.things.cgomp.common.mq.message.TradingRecordConfirmReqMsg;
import com.things.cgomp.common.mq.message.order.EndChargingReqMsg;
import com.things.cgomp.order.api.dto.AddOrderDTO;
import com.things.cgomp.order.api.enums.OccupyTypeEnum;
import com.things.cgomp.order.api.enums.OrderSnPrefixEnum;
import com.things.cgomp.order.api.enums.OrderStateEnum;
import com.things.cgomp.order.api.enums.OrderTypeEnum;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.enums.*;
import com.things.cgomp.order.service.IOrderInfoService;
import com.things.cgomp.order.service.impl.order.OrderState;
import com.things.cgomp.system.api.RemoteAppUserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class TradingRecordConfirmOrderState extends OrderState {

    @Resource
    private RemoteAppUserService remoteAppUserService;

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
    public OrderInfo confirmTradingRecord(
            OrderInfo orderInfo,
            TradingRecordConfirmReqMsg reqMsg
    ) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void drawGun(
            OrderInfo orderInfo,
            DrawGunReqMsg reqMsg
    ) {
        if (orderInfo == null) {
            addPreviousOccupy(
                    reqMsg
            );
            return;
        }

        updateOrderData(
                orderInfo,
                reqMsg
        );
    }

    private void addPreviousOccupy(
            DrawGunReqMsg reqMsg
    ) {
        DeviceInfo pileDevice = getDeviceInfo(reqMsg.getPileId());
        DeviceInfo portDevice = getDeviceInfo(reqMsg.getPortId());

        if(pileDevice == null || portDevice == null){
            return;
        }

        BigDecimal occupyFee = calculatePreviousOccupyFee(
                reqMsg,
                pileDevice
        );

        if(occupyFee != null){
            addPreviousOccupy(
                    reqMsg,
                    pileDevice,
                    portDevice,
                    occupyFee
            );
        }
    }

    private BigDecimal calculatePreviousOccupyFee(
            DrawGunReqMsg reqMsg,
            DeviceInfo pileDevice
    ) {
        LocalDateTime insertTime = DateUtils.toLocalDateTime(reqMsg.getInsertTime());
        LocalDateTime drawGunTime = DateUtils.toLocalDateTime(reqMsg.getDrawGunTime());
        return siteOccupyFeeService.calculateFee(
                pileDevice.getSiteId(),
                OccupyTypeEnum.BEFORE_CHARGING.getType(),
                insertTime,
                drawGunTime
        );
    }

    private OrderInfo addPreviousOccupy(
            DrawGunReqMsg reqMsg,
            DeviceInfo pileDevice,
            DeviceInfo portDevice,
            BigDecimal occupyFee
    ) {
        LocalDateTime insertTime = DateUtils.toLocalDateTime(reqMsg.getInsertTime());
        LocalDateTime drawGunTime = DateUtils.toLocalDateTime(reqMsg.getDrawGunTime());

        IOrderInfoService orderInfoService = getOrderInfoService();

        Long userId = orderInfoService.selectLatestUserId(reqMsg.getVin());

        String licensePlateNumber = getLicensePlateNumber(userId);
        String phone = getPhone(userId);

        OrderInfo insertOrder = new OrderInfo();
        insertOrder.setSn(OrderSnPrefixEnum.OCCUPY_BEFORE_CHARGING.getPrefix() + reqMsg.getOrderNo())
                .setTradeSn(reqMsg.getOrderNo())
                .setOrderType(OrderTypeEnum.OCCUPY.getType())
                .setOccupyType(OccupyTypeEnum.BEFORE_CHARGING.getType())
                .setPileId(pileDevice.getDeviceId())
                .setPortId(portDevice.getDeviceId())
                .setRealStartTime(insertTime)
                .setRealEndTime(drawGunTime)
                .setIsFee(pileDevice.getIsFree())
                .setUserId(userId)
                .setPhone(phone)
                .setLicensePlateNumber(licensePlateNumber)
                .setVin(reqMsg.getVin())
                .setSiteId(pileDevice.getSiteId())
                .setInsertTime(insertTime)
                .setDrawGunTime(drawGunTime)
                .setCreateTime(insertTime)
                .setOrderAmount(occupyFee)
                .setEndReasonCode(EndReasonEnum.DRAWN_GUN_NOT_CHARGED.getType())
                .setEndReasonDesc(EndReasonEnum.DRAWN_GUN_NOT_CHARGED.getDescription())
                .setProcessLoss(EnableEnum.ENABLE.getCode())
                .setOrderState(OrderStateEnum.GUN_DRAWN.getType());
        insertOrder.setRealChargingTime();

        BigDecimal payAmount = getPayAmount(
                insertOrder,
                occupyFee
        );

        insertOrder.setPayAmount(payAmount);

        orderInfoService.save(insertOrder);

        orderLogService.saveLog(
                insertOrder.getId(),
                insertTime,
                OrderLogEnum.INSERT_GUN
        );

        orderLogService.saveLog(
                insertOrder.getId(),
                drawGunTime,
                OrderLogEnum.DRAW_GUN
        );

        return orderInfoService.getById(insertOrder.getId());
    }

    private String getPhone(Long userId) {
        AppUser user = getUser(userId);
        if(user == null){
            return null;
        }

        return user.getMobile();
    }

    private AppUser getUser(Long userId) {
        if(userId == null){
            return null;
        }

        R<AppUser> userR = remoteAppUserService.getUserInfo(userId);

        return userR.getData();
    }

    private String getLicensePlateNumber(Long userId) {
        AppUserCar car = getAppUserCar(userId);
        if(car == null){
            return null;
        }

        return car.getLicensePlateNumber();
    }

    private AppUserCar getAppUserCar(Long userId) {
        if(userId == null){
            return null;
        }
        R<AppUserCar> defaultCarR = remoteAppUserService.selectDefaultCar(userId);
        return defaultCarR.getData();
    }

    private void updateOrderData(
            OrderInfo orderInfo,
            DrawGunReqMsg reqMsg
    ) {
        if(orderInfo.getDrawGunTime() != null){
            return;
        }

        OrderInfo updatedOrder = updateChargeOrderData(
                orderInfo,
                reqMsg
        );

        BigDecimal occupyFee = siteOccupyFeeService.calculateFee(
                orderInfo.getSiteId(),
                OccupyTypeEnum.AFTER_CHARGING.getType(),
                updatedOrder.getSettlementTime(),
                updatedOrder.getDrawGunTime()
        );

        if(occupyFee != null){
            addAfterOccupy(
                    updatedOrder,
                    occupyFee
            );
        }
    }

    private void addAfterOccupy(
            OrderInfo parentOrder,
            BigDecimal occupyFee
    ) {
        DeviceInfo pileDevice = getDeviceInfo(parentOrder.getPileId());

        DeviceInfo portDevice = getDeviceInfo(parentOrder.getPortId());
        if(pileDevice == null || portDevice == null){
            return;
        }

        addAfterOccupy(
                parentOrder,
                pileDevice,
                portDevice,
                occupyFee
        );
    }

    private OrderInfo addAfterOccupy(
            OrderInfo parentOrder,
            DeviceInfo pileDevice,
            DeviceInfo portDevice,
            BigDecimal occupyFee
    ) {

        OrderInfo insertOrder = new OrderInfo();
        insertOrder.setParentId(parentOrder.getId())
                .setSn(OrderSnPrefixEnum.OCCUPY_AFTER_CHARGING.getPrefix() + parentOrder.getSn())
                .setTradeSn(parentOrder.getTradeSn())
                .setOrderType(OrderTypeEnum.OCCUPY.getType())
                .setOccupyType(OccupyTypeEnum.AFTER_CHARGING.getType())
                .setOrderSource(parentOrder.getOrderSource())
                .setPileId(pileDevice.getDeviceId())
                .setPortId(portDevice.getDeviceId())
                .setRealStartTime(parentOrder.getRealEndTime())
                .setRealEndTime(parentOrder.getDrawGunTime())
                .setIsFee(pileDevice.getIsFree())
                .setDeviceType(parentOrder.getDeviceType())
                .setUserId(parentOrder.getUserId())
                .setPhone(parentOrder.getPhone())
                .setLicensePlateNumber(parentOrder.getLicensePlateNumber())
                .setVin(parentOrder.getVin())
                .setSiteId(pileDevice.getSiteId())
                .setSettlementTime(parentOrder.getSettlementTime())
                .setDrawGunTime(parentOrder.getDrawGunTime())
                .setCreateTime(parentOrder.getSettlementTime())
                .setOrderAmount(occupyFee)
                .setEndReasonCode(EndReasonEnum.DRAWN_GUN_AFTER_CHARGING.getType())
                .setEndReasonDesc(EndReasonEnum.DRAWN_GUN_AFTER_CHARGING.getDescription())
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
                OrderLogEnum.TRADING_RECORD_CONFIRM
        );

        orderLogService.saveLog(
                insertOrder.getId(),
                parentOrder.getRealStartTime(),
                OrderLogEnum.DRAW_GUN
        );

        return orderInfoService.getById(insertOrder.getId());
    }

    private OrderInfo updateChargeOrderData(
            OrderInfo orderInfo,
            DrawGunReqMsg reqMsg
    ) {
        LocalDateTime drawGunTime = DateUtils.toLocalDateTime(reqMsg.getDrawGunTime());

        IOrderInfoService orderInfoService = getOrderInfoService();
        OrderInfo updateOrder = new OrderInfo()
                .setId(orderInfo.getId())
                .setVersion(orderInfo.getVersion())
                .setDrawGunTime(drawGunTime)
                .setOrderState(OrderStateEnum.GUN_DRAWN.getType());
        orderInfoService.updateById(updateOrder);

        orderLogService.saveLog(
                orderInfo.getId(),
                drawGunTime,
                OrderLogEnum.DRAW_GUN
        );

        return orderInfoService.getById(orderInfo.getId());
    }
}
