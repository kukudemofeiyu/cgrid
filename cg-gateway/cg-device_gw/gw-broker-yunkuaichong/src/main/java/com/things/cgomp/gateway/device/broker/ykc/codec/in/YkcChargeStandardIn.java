package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import lombok.Data;

import java.io.Serializable;

/**
 */
@Data
public class YkcChargeStandardIn extends YkcMessageIn implements Serializable {
    /**
     *  设备编号
     */
    private String deviceNo;
}
