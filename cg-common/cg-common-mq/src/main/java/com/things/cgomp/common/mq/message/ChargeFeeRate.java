package com.things.cgomp.common.mq.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 充电费率信息上报
 * @author things
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargeFeeRate implements Serializable {

    /**
     * 费率单价
     */
    private BigDecimal unitPrice;
    /**
     * 费率电量
     */
    private BigDecimal energy;
    /**
     * 计损电量
     */
    private BigDecimal loseEnergy;
    /**
     * 费率金额
     */
    private BigDecimal amount;
}
