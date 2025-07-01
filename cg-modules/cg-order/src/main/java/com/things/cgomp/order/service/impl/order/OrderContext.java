package com.things.cgomp.order.service.impl.order;

import com.things.cgomp.order.api.enums.OrderStateEnum;
import com.things.cgomp.order.service.impl.order.impl.EndChargingOrderState;
import com.things.cgomp.order.service.impl.order.impl.ChargingOrderState;
import com.things.cgomp.order.service.impl.order.impl.InsertGunOrderState;
import com.things.cgomp.order.service.impl.order.impl.TradingRecordConfirmOrderState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OrderContext {

    private final Map<Integer, OrderState> states;

    public OrderContext(
            InsertGunOrderState insertGunOrderState,
            ChargingOrderState chargingOrderState,
            EndChargingOrderState endChargingOrderState,
            TradingRecordConfirmOrderState tradingRecordConfirmOrderState
    ) {
        states = new HashMap<>();
        states.put(OrderStateEnum.INSERT_GUN.getType(), insertGunOrderState);
        states.put(OrderStateEnum.CHARGING.getType(), chargingOrderState);
        states.put(OrderStateEnum.END_CHARGING.getType(), endChargingOrderState);
        states.put(OrderStateEnum.TRADING_RECORD_CONFIRM.getType(), tradingRecordConfirmOrderState);
    }

    public OrderState getState(Integer orderStateCode) {
        return states.get(orderStateCode);
    }
}
