package com.things.cgomp.gateway.device.broker.ykc.codec.out;

import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 设备停止充电
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class YkcDeviceStopChargeOut extends YkcMessageOut  {
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 枪号
     */
    private String gunNo;

    public YkcDeviceStopChargeOut(Integer frameSerialNo, String deviceNo, String gunNo) {
        super(frameSerialNo, DeviceOpConstantEnum.STOP_CHARGE.getOpCode(),true);
        this.deviceNo = deviceNo;
        this.gunNo = gunNo;
    }
}
