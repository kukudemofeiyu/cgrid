package com.things.cgomp.device.enums;

import lombok.Getter;

@Getter
public enum DeviceStatusEnum {

    DISABLE(0, "禁用"),
    AVAILABLE(1, "可用"),
    DELETED(2, "已删除"),
    ;
    private final Integer type;

    private final String description;

    DeviceStatusEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }
}