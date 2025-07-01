package com.things.cgomp.device.dto.statistics;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author things
 */
@Data
public class StatisticsOrderTrendHourResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 小时
     * 格式：HH:mm
     */
    private String hour;
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
