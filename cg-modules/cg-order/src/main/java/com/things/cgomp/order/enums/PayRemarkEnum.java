package com.things.cgomp.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum PayRemarkEnum {

    OCCUPY("占位费扣款"),
    CHARGING_ORDER("充电订单扣款"),
    ;

    private final String desc;
}
