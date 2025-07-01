package com.things.cgomp.pay.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum SiteActivityTypeEnum {

    SITE_DISCOUNT(0, "站点折扣"),
    SERVICE_DISCOUNT(1, "服务费折扣"),
    SITE_FIXED_PRICE(2, "站点一口价"),
    SERVICE_FIXED_PRICE(3, "服务费一口价"),
    ;
    private final Integer type;

    private final String description;

    public static final List<Integer> DISCOUNT_TYPES = Arrays.asList(
            SITE_DISCOUNT.getType(),
            SERVICE_DISCOUNT.getType()
    );

    public static final List<Integer> TOTAL_COST_TYPES = Arrays.asList(
            SITE_DISCOUNT.getType(),
            SITE_FIXED_PRICE.getType()
    );

    public static final List<Integer> SERVICE_FEE_TYPES = Arrays.asList(
            SERVICE_DISCOUNT.getType(),
            SERVICE_FIXED_PRICE.getType()
    );
}