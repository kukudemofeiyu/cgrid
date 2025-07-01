package com.things.cgomp.pay.api.enums;

import lombok.Getter;

@Getter
public enum DiscountTypeEnum {

    DEDUCTION(0, "抵扣"),
    DISCOUNT(1, "折扣"),
    FIXED_PRICE(2, "一口价"),
    ;
    private final Integer type;

    private final String description;

    DiscountTypeEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    public static Integer getDiscountTypeByActivityType(Integer activityType) {
        if (SiteActivityTypeEnum.DISCOUNT_TYPES.contains(activityType)) {
            return DiscountTypeEnum.DISCOUNT.getType();
        }

        return DiscountTypeEnum.FIXED_PRICE.getType();
    }

    public static Integer getDiscountTypeByCouponType(Integer couponType) {
        if (CouponTypeEnum.CASH.getType().equals(couponType)) {
            return DiscountTypeEnum.DEDUCTION.getType();
        }

        return DiscountTypeEnum.DISCOUNT.getType();
    }
}