package com.things.cgomp.common.device.enums;


import lombok.Getter;

@Getter
public enum DeviceNetWorkState {

    onLine(1),
    offLine(0),
    unknown(2);

    private final Integer value;

    DeviceNetWorkState(Integer value) {
        this.value = value;
    }
}
