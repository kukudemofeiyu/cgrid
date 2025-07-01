package com.things.cgomp.gateway.device.broker.ykc.codec.out;

import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class YkcDeviceGetChargeRecordOut extends YkcMessageOut{

    private String orderNo;

    private String deviceNo;

    private String gunNo;

    public static YkcDeviceGetChargeRecordOut of(Integer frameSerialNo, String orderNo, String deviceNo, String gunNo) {

        return new YkcDeviceGetChargeRecordOut(frameSerialNo,orderNo,deviceNo,gunNo );
    }


    public  YkcDeviceGetChargeRecordOut (Integer frameSerialNo,  String orderNo, String deviceNo, String gunNo) {
        super(frameSerialNo, DeviceOpConstantEnum.GET_CHARGE_RECORD.getOpCode(), true);
        this.orderNo = orderNo;
        this.deviceNo = deviceNo;
        this.gunNo = gunNo;
    }

}
