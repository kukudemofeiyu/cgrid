package com.things.cgomp.gateway.device.broker.ykc.codec.out;

import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 时间同步
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class YkcDeviceTimeSyncOut extends YkcMessageOut  {
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 时间
     */
    private LocalDateTime time;

    public YkcDeviceTimeSyncOut(Integer frameSerialNo, String deviceNo, LocalDateTime time) {
        super(frameSerialNo, DeviceOpConstantEnum.DEVICE_TIME_SYNC.getOpCode(),false);
        this.deviceNo = deviceNo;
        this.time = time;
    }
}
