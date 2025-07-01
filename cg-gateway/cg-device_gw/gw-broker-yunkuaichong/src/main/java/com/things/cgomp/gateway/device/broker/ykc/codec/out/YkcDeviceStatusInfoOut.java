package com.things.cgomp.gateway.device.broker.ykc.codec.out;

import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 设备状态信息
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class YkcDeviceStatusInfoOut extends YkcMessageOut  {
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 枪编号
     */
    private String gunNo;

    public YkcDeviceStatusInfoOut(Integer frameSerialNo, String deviceNo, String gunNo) {
        super(frameSerialNo, DeviceOpConstantEnum.DEVICE_STATUS_READ.getOpCode(),true);
        this.deviceNo = deviceNo;
        this.gunNo = gunNo;
    }
}
