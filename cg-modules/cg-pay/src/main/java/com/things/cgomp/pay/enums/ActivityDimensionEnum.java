package com.things.cgomp.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActivityDimensionEnum {

    All(0, "全部站点"),
    PART(1, "部分站点"),
    ;
    private final Integer type;

    private final String description;
}