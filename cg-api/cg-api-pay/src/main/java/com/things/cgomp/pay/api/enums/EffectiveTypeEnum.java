package com.things.cgomp.pay.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EffectiveTypeEnum {
    ALL_DAY(0, "全天"),
    TIME_FRAME(1, "时段"),
    ;
    private final Integer type;

    private final String description;
}