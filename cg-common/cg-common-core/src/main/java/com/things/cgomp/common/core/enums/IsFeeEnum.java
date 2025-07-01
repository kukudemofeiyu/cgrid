package com.things.cgomp.common.core.enums;

import lombok.Getter;

@Getter
public enum IsFeeEnum {

    FREE(0, "不收费"),
    CHARGE(1, "收费"),
    ;
    private final Integer type;

    private final String description;

    IsFeeEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }
}