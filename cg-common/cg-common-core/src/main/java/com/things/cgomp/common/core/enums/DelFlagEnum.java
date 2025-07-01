package com.things.cgomp.common.core.enums;

import lombok.Getter;

@Getter
public enum DelFlagEnum {

    EXIST(0, "存在"),
    DELETE(1, "删除"),
    ;
    private final Integer type;

    private final String description;

    DelFlagEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }
}