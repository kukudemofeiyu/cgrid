package com.things.cgomp.common.device.enums;

import lombok.Getter;

@Getter
public enum PortStatusEnum {

    OFF_LINE(0, "离线"),
    FAULT(1, "故障"),
    FREE(2, "空闲"),
    CHARGE(3, "充电"),
    LOADED(4, "已插枪"),
    OCCUPY(5, "占用"),
    ;
    private final Integer type;

    private final String description;

    PortStatusEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }
    public static PortStatusEnum getPortStatusEnum(Integer type) {
        for (PortStatusEnum portStatusEnum : PortStatusEnum.values()) {
            if (portStatusEnum.getType().equals(type)) {
                return portStatusEnum;
            }
        }
        return null;
    }
}