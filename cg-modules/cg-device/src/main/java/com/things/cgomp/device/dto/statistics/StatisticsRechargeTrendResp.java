package com.things.cgomp.device.dto.statistics;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author things
 */
@Data
public class StatisticsRechargeTrendResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日期
     * 格式：yyyy-MM-dd
     */
    private String date;
    /**
     * 充值金额
     */
    private BigDecimal rechargeAmount;

    public void setRechargeAmount(BigDecimal rechargeAmount) {
        this.rechargeAmount = rechargeAmount.setScale(2, RoundingMode.DOWN);
    }
}
