package com.things.cgomp.common.device.enums;

import lombok.Getter;

@Getter
public enum DeviceAlarmTypeEnum {

    carError(0x01,"车故障"),
    carAndDeviceError(0x02, "车桩交互故障"),
    platformError(0x03, "桩/平台故障"),
    deviceError(0x04, "桩故障"),
    otherError(0x05, "自定义故障"),

    ;

    private Integer type;

    private String typeDesc;

    DeviceAlarmTypeEnum(Integer type, String typeDesc) {
        this.type = type;
        this.typeDesc = typeDesc;
    }

    public static String getTypeDesc(Integer type){
        DeviceAlarmTypeEnum[] values = DeviceAlarmTypeEnum.values();
        for (DeviceAlarmTypeEnum deviceAlarmTypeEnum:
        values) {
            if(deviceAlarmTypeEnum.getType().equals(type)){
                return deviceAlarmTypeEnum.getTypeDesc();
            }
        }

        return null;

    }

}
