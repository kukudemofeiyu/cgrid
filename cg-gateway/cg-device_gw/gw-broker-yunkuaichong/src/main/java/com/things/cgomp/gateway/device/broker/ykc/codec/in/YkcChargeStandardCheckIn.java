package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 充电标准校验
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YkcChargeStandardCheckIn extends  YkcMessageIn implements Serializable {
    /**
     *  设备编号
     */
    private String deviceNo;
    /**
     * 计费模型编号
     */
    private Integer chargeStandardModelNo;
}
