package com.things.cgomp.order.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OccupyTypeEnum {

    BEFORE_CHARGING(0, "充电前占位"),
    AFTER_CHARGING(1, "充电后占位"),
    ;
    private final Integer type;

    private final String description;

}