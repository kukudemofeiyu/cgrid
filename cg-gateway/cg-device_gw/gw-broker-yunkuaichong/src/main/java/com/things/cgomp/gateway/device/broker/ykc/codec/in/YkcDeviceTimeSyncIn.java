package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 时间同步
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YkcDeviceTimeSyncIn extends YkcMessageIn {
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 时间
     */
    private LocalDateTime time;
}
