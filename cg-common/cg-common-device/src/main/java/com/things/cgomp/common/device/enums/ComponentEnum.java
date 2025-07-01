package com.things.cgomp.common.device.enums;

import lombok.Getter;

@Getter
public enum ComponentEnum {

    PILE(0, "桩"),
    PORT(1, "枪"),
    ;
    private final Integer type;

    private final String description;

    ComponentEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }
}