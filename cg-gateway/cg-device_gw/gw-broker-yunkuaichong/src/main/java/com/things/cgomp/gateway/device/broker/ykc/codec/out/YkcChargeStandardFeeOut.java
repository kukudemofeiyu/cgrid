package com.things.cgomp.gateway.device.broker.ykc.codec.out;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class YkcChargeStandardFeeOut implements Serializable {

    /**
     * 服务费
     */
    private BigDecimal serviceFee;

    /**
     * 电费
     */
    private BigDecimal ElectricityFee;

    private Integer feeNo;
}
