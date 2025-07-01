package com.things.cgomp.gateway.device.broker.ykc.codec.out;

import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import lombok.Data;

@Data
public class YkcDeviceAlarmStatusOut  extends YkcMessageOut{

    private String deviceNo;

    private String gunNo;

    private Boolean ack;

    public  YkcDeviceAlarmStatusOut (Integer frameSerialNo,  String deviceNo, String gunNo, Boolean ack) {
        super(frameSerialNo, DeviceOpConstantEnum.DEVICE_ALARM_REPORT_RESP.getOpCode(), true);
        this.deviceNo = deviceNo;
        this.gunNo = gunNo;
        this.ack = ack;
    }
}
