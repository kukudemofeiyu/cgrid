package com.things.cgomp.gateway.device.broker.ykc.codec.out;

import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import lombok.Data;

@Data
public class YkcDeviceAlarmRecoverOut extends YkcMessageOut {

    private String deviceNo;

    private String gunNo;

    private Boolean ack;

    public  YkcDeviceAlarmRecoverOut (Integer frameSerialNo,  String deviceNo, String gunNo, Boolean ack) {
        super(frameSerialNo, DeviceOpConstantEnum.DEVICE_ALARM_RECOVER_RESP.getOpCode(), true);
        this.deviceNo = deviceNo;
        this.gunNo = gunNo;
        this.ack = ack;
    }


}
