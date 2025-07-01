package com.things.cgomp.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReceiveLimitTypeEnum {

    NO_LIMIT(1, "不限制"),
    DAY_LIMIT(2, "设置次数（次/人/天）"),
    TOTAL_LIMIT(3, "设置次数（次/人/活动周期）"),
    ;
    private final Integer type;

    private final String description;

    public static ReceiveLimitTypeEnum getEnum(Integer type) {
        for (ReceiveLimitTypeEnum typeEnum : ReceiveLimitTypeEnum.values()) {
            if (typeEnum.getType().equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }
}