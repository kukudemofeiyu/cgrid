package com.things.cgomp.order.enums;

import lombok.Getter;

@Getter
public enum ProcessStepEnum {

    FIGURED_OUT(0, "处理完毕"),
    UNRETURNED_INTEGRAL(1, "未返积分"),
    NO_DATA_STATISTICS(2, "未数据统计"),
    UNDIVIDED_COMMISSION(3, "未分佣"),
    ;
    private final Integer type;

    private final String description;

    ProcessStepEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }
}