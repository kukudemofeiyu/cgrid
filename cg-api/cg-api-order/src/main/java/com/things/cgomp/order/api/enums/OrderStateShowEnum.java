package com.things.cgomp.order.api.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum OrderStateShowEnum {

    UNDER_WAY(
            1,
            "进行中",
            Arrays.asList(
                    OrderStateEnum.INSERT_GUN.getType(),
                    OrderStateEnum.CHARGING.getType()
            )
    ),
    COMPLETED(
            2,
            "已完成",
            Arrays.asList(
                    OrderStateEnum.END_CHARGING.getType(),
                    OrderStateEnum.TRADING_RECORD_CONFIRM.getType(),
                    OrderStateEnum.GUN_DRAWN.getType()
            )
    ),
    ;
    private final Integer type;

    private final String description;

    private final List<Integer> orderStates;

    OrderStateShowEnum(
            Integer type,
            String description,
            List<Integer> orderStates
    ) {
        this.type = type;
        this.description = description;
        this.orderStates = orderStates;
    }

    public static Integer getOrderStateShow(
            Integer orderState
    ) {
        if(orderState == null){
            return null;
        }

        for (OrderStateShowEnum typeEnum : OrderStateShowEnum.values()) {
            if (typeEnum.getOrderStates().contains(orderState)) {
                return typeEnum.getType();
            }
        }

        return null;
    }

}