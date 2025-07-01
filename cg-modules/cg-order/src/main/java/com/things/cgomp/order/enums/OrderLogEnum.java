package com.things.cgomp.order.enums;

import lombok.Getter;

@Getter
public enum OrderLogEnum {

    INSERT_GUN("插枪", "客户插入充电枪"),
    CHARGING("开始充电", "客户启动充电"),
    END_CHARGING("结束充电", "结束充电"),
    TRADING_RECORD_CONFIRM("交易记录确认","交易记录确认" ),
    PAYMENT_SUCCESS("支付成功","客户支付成功" ),
    DRAW_GUN("拔枪","客户拔枪" ),
    ;
    private final String tile;

    private final String content;

    OrderLogEnum(String tile, String content) {
        this.tile = tile;
        this.content = content;
    }
}