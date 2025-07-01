package com.things.cgomp.order.service.impl.order.impl;

import com.things.cgomp.common.core.utils.DateUtils;
import com.things.cgomp.common.mq.message.DrawGunReqMsg;
import com.things.cgomp.common.mq.message.TradingRecordConfirmReqMsg;
import com.things.cgomp.common.mq.message.order.EndChargingReqMsg;
import com.things.cgomp.order.api.dto.AddOrderDTO;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.enums.OrderLogEnum;
import com.things.cgomp.order.api.enums.OrderStateEnum;
import com.things.cgomp.order.service.IOrderInfoService;
import com.things.cgomp.order.service.impl.order.OrderState;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ChargingOrderState extends OrderState {

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
        LocalDateTime endTime = DateUtils.toLocalDateTime(reqMsg.getEndTime());

        if (orderInfo.getRealEndTime() == null) {
            IOrderInfoService orderInfoService = getOrderInfoService();
            OrderInfo updateOrder = new OrderInfo()
                    .setId(orderInfo.getId())
                    .setVersion(orderInfo.getVersion())
                    .setRealEndTime(endTime)
                    .setOrderState(OrderStateEnum.END_CHARGING.getType());;
            orderInfoService.updateById(updateOrder);
        }

        orderLogService.saveLog(
                orderInfo.getId(),
                endTime,
                OrderLogEnum.END_CHARGING
        );
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
