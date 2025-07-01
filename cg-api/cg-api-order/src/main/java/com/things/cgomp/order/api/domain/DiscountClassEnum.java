package com.things.cgomp.order.api.domain;

import lombok.Getter;

@Getter
public enum DiscountClassEnum {

    SITE(1, "站点活动"),
    COUPON(2, "优惠券"),
    ;
    private final Integer type;

    private final String description;

    DiscountClassEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }
}