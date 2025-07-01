package com.things.cgomp.device.dto.statistics;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author things
 */
@Data
public class StatisticsManageDataResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总收益
     */
    private BigDecimal totalIncome;
    /**
     * 实际收益
     */
    private BigDecimal realIncome;
    /**
     * 分红金额
     */
    private BigDecimal commissionAmount;
    /**
     * 退款金额
     */
    private BigDecimal refundAmount;
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
     */
    private BigDecimal chargeTime;
    /**
     * 总耗电量
     */
    private BigDecimal consumeElectricity;
    /**
     * 设备总数
     */
    private Long deviceCount = 0L;
    /**
     * 在线设备数量
     */
    private Long onlineCount = 0L;
    /**
     * 离线设备数量
     */
    private Long offlineCount = 0L;
    /**
     * 故障设备数量
     */
    private Long faultCount = 0L;

    public void setCommissionAmount(BigDecimal commissionAmount) {
        this.commissionAmount = commissionAmount.setScale(2, RoundingMode.DOWN);
    }

    public void setChargeFee(BigDecimal chargeFee) {
        this.chargeFee = chargeFee.setScale(2, RoundingMode.DOWN);
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee.setScale(2, RoundingMode.DOWN);
    }

    public void setChargeTime(BigDecimal chargeTime) {
        this.chargeTime = chargeTime.setScale(2, RoundingMode.DOWN);;
    }
}
