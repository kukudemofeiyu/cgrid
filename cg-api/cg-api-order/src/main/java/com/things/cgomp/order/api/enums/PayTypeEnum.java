package com.things.cgomp.order.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayTypeEnum {

    WE_CHAT(0,"微信支付"),
    CARD(1,"卡支付"),
    WALLET(2,"账户余额"),
    ;
    private final Integer type;
    private final String description;

}