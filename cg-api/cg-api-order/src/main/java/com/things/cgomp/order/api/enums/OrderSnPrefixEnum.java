package com.things.cgomp.order.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderSnPrefixEnum {

    CHARGING("CD0", "充电订单"),
    OCCUPY_BEFORE_CHARGING("ZW0", "充电前占位"),
    OCCUPY_AFTER_CHARGING("ZW1", "充电后占位"),
    ;
    private final String prefix;

    private final String description;

}