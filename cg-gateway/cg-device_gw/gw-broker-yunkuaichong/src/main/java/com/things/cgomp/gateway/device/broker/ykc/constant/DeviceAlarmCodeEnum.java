package com.things.cgomp.gateway.device.broker.ykc.constant;

import lombok.Getter;

@Getter
public enum DeviceAlarmCodeEnum {

    /**
     * 车故障
     */
    error0x0001(0x0001, "BMS通讯异常");


    private Integer alarmCode;
    private String alarmReason;

    DeviceAlarmCodeEnum(Integer alarmCode, String alarmReason) {
        this.alarmCode = alarmCode;
        this.alarmReason = alarmReason;
    }


    public static String getReason(Integer alarmCode){
        DeviceAlarmCodeEnum[] values = DeviceAlarmCodeEnum.values();
        for (DeviceAlarmCodeEnum alarmCodeEnum:
             values) {
            if(alarmCodeEnum.getAlarmCode().equals(alarmCode)){
                return alarmCodeEnum.getAlarmReason();
            }
        }

        return null;
    }

}
