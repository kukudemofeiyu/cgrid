package com.things.cgomp.gateway.device.broker.ykc.codec.out;

import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 心跳
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class YkcDeviceHeartbeatOut extends YkcMessageOut  {
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 枪号
     */
    private String gunNo;
    /**
     * 确认
     */
    private Byte ack;

    public static YkcDeviceHeartbeatOut builder(Integer frameSerialNo, String deviceNo, String gunNo) {
        return new YkcDeviceHeartbeatOut(frameSerialNo, deviceNo, gunNo);
    }

    private YkcDeviceHeartbeatOut(Integer frameSerialNo, String deviceNo, String gunNo) {
        super(frameSerialNo, DeviceOpConstantEnum.HEARTBEAT_RESP.getOpCode(),false);
        this.deviceNo = deviceNo;
        this.gunNo = gunNo;
        this.ack = 0x00;
    }
}
