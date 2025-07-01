package com.things.cgomp.order.api.enums;

import lombok.Getter;

@Getter
public enum OrderStateEnum {
    INSERT_GUN(0, "插枪"),
    CHARGING(1, "充电中"),
    END_CHARGING(2, "结束充电"),
    TRADING_RECORD_CONFIRM(3, "交易记录确认"),
    GUN_DRAWN(4, "已拔枪"),
    ;
    private final Integer type;

    private final String description;

    OrderStateEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }
}