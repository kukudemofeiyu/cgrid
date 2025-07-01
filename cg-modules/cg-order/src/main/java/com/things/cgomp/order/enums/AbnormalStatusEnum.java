package com.things.cgomp.order.enums;

import lombok.Getter;

@Getter
public enum AbnormalStatusEnum {

    NORMAL(0, "正常"),
    ABNORMAL(1, "异常"),
    ;
    private final Integer type;

    private final String description;

    AbnormalStatusEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }
}