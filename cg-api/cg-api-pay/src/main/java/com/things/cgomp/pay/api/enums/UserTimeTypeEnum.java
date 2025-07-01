package com.things.cgomp.pay.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserTimeTypeEnum {

    RELATIVE_TIME(0, "相对时间"),
    ABSOLUTE_TIME(1, "绝对时间"),
    ;
    private final Integer type;

    private final String description;
}