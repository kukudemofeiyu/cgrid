package com.things.cgomp.pay.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CouponTypeEnum {

    CASH(0, "现金券"),
    DISCOUNT(1, "折扣券"),
    ;
    private final Integer type;

    private final String description;
}