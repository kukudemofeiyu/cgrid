package com.things.cgomp.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChargeDimensionEnum {

    ELECTRICITY(0, "度数维度"),
    MONEY(1, "金额维度"),
    ;
    private final Integer type;

    private final String description;
}