package com.things.cgomp.order.vo;

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
public class CommissionRecordTotalVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单分成总金额
     */
    private BigDecimal orderTotalAmount;
    /**
     * 平台分成总金额
     */
    private BigDecimal platformTotalAmount;
    /**
     * 代理商分成总金额
     */
    private BigDecimal operatorTotalAmount;

    public void setOrderTotalAmount(BigDecimal orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount.setScale(2, RoundingMode.DOWN);
    }

    public void setPlatformTotalAmount(BigDecimal platformTotalAmount) {
        this.platformTotalAmount = platformTotalAmount.setScale(2, RoundingMode.DOWN);
    }

    public void setOperatorTotalAmount(BigDecimal operatorTotalAmount) {
        this.operatorTotalAmount = operatorTotalAmount.setScale(2, RoundingMode.DOWN);
    }
}
