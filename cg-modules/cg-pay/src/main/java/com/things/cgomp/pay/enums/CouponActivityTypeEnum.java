package com.things.cgomp.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CouponActivityTypeEnum {

    FIRST_CHARGE(1, "首次充电活动"),
    SINGLE_CHARGE(2, "单次充电活动"),
    INTERNAL_COUPON(3, "内部发券活动"),
    ;
    private final Integer type;

    private final String description;

    public static CouponActivityTypeEnum getEnum(Integer type) {
        for (CouponActivityTypeEnum typeEnum : CouponActivityTypeEnum.values()) {
            if (typeEnum.getType().equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }
}