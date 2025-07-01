package com.things.cgomp.order.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderTypeEnum {

    REAL_TIME(0, "充电订单"),
    APPOINTMENT(1, "预约订单"),
    OCCUPY(2, "占位订单"),
    ;
    private final Integer type;

    private final String description;

}