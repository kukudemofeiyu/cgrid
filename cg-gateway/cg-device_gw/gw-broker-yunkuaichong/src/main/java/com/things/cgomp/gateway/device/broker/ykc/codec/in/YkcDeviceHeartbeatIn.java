package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 心跳
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YkcDeviceHeartbeatIn extends YkcMessageIn  {
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 枪号
     */
    private String gunNo;
    /**
     * 枪是否正常
     */
    private Boolean gunNormal;
}
