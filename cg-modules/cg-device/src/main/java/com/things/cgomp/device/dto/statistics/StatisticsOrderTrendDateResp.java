package com.things.cgomp.device.dto.statistics;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author things
 */
@Data
@Accessors(chain = true)
public class StatisticsOrderTrendDateResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日期
     * 格式：yyyy-MM-dd
     */
    private String date;
    /**
     * 充电次数
     */
    private Long chargeCount;
    /**
     * 充电金额
     */
    private BigDecimal chargeAmount;
    /**
     * 充电量
     */
    private BigDecimal chargeElectricity;

    public void setChargeAmount(BigDecimal chargeAmount) {
        this.chargeAmount = chargeAmount.setScale(2, RoundingMode.DOWN);
    }

    public void setChargeElectricity(BigDecimal chargeElectricity) {
        this.chargeElectricity = chargeElectricity.setScale(2, RoundingMode.DOWN);
    }
}
