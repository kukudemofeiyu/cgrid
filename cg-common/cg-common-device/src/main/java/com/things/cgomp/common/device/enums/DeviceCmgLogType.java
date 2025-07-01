package com.things.cgomp.common.device.enums;

/**
 * 设备交互日志上下行枚举类
 */
public enum DeviceCmgLogType {

    upLink(1),
    downLink(2),

    ;

    private final Integer value;

    DeviceCmgLogType(Integer value) {
        this.value = value;
    }
}
