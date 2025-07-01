package com.things.cgomp.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum EnableEnum {

    DISABLE(0, "否"),
    ENABLE(1, "是"),
    ;
    private final Integer code;

    private final String description;

}
