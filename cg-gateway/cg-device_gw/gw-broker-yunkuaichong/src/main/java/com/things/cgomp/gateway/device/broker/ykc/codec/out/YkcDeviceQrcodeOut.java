package com.things.cgomp.gateway.device.broker.ykc.codec.out;

import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 设备二维码
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class YkcDeviceQrcodeOut extends YkcMessageOut  {
    private String deviceNo;

    private String gunNo;

    private Long portId;

    public static YkcDeviceQrcodeOut of(Integer frameSerialNo, String deviceNo, String gunNo, Long portId) {
        return new YkcDeviceQrcodeOut(frameSerialNo, deviceNo, gunNo,  portId);
    }

    private YkcDeviceQrcodeOut(Integer frameSerialNo, String deviceNo, String gunNo, Long portId) {
        super(frameSerialNo, DeviceOpConstantEnum.UPDATE_DEVICE_QR.getOpCode(),true);
        this.deviceNo = deviceNo;
        this.gunNo = gunNo;
        this.portId = portId;
    }
}
