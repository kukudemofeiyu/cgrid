package com.things.cgomp.order.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author things
 */
@Data
public class ManageBaseData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 电费
     */
    private BigDecimal chargeFee;
    /**
     * 服务费
     */
    private BigDecimal serviceFee;
    /**
     * 订单数量
     */
    private Long orderNum;
    /**
     * 充电次数
     */
    private Long chargeCount;
    /**
     * 充电时长
     * 小时
     */
    private BigDecimal chargeTime;
    /**
     * 总耗电量
     */
    private BigDecimal consumeElectricity;

    public void setChargeFee(BigDecimal chargeFee) {
        this.chargeFee = chargeFee.setScale(2, RoundingMode.DOWN);
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee.setScale(2, RoundingMode.DOWN);
    }

    public void setChargeHour(BigDecimal chargeTime) {
        this.chargeTime = chargeTime.setScale(2, RoundingMode.DOWN);
    }
}
