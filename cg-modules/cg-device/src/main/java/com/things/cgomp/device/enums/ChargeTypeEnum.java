package com.things.cgomp.device.enums;


import lombok.Getter;

@Getter
public enum ChargeTypeEnum {

    FAST(0, "快充"),
    SLOW(1, "慢充");

    private final Integer type;

    private final String description;

    ChargeTypeEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }
}
