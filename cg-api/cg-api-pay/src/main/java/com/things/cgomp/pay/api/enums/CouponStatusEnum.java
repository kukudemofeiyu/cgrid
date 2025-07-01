package com.things.cgomp.pay.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CouponStatusEnum {

    UNUSED(0, "未使用"),
    USED(1, "已使用"),
    EXPIRATION(2, "失效"),
    ;
    private final Integer type;

    private final String description;
}