package com.things.cgomp.device.dto.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.things.cgomp.common.core.utils.StatisticsUtils.buildChargeTime;

/**
 * @author things
 */
@Data
@Accessors(chain = true)
public class StatisticsSiteDataResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 充电次数
     */
    private Integer chargeCount;
    /**
     * 充电量
     */
    private BigDecimal chargeElectricity;
    /**
     * 充电时长
     */
    @JsonIgnore
    private BigDecimal chargeHour;
    /**
     * 前端展示
     * 充电时长（xx小时xx分钟）
     */
    private String chargeTime;
    /**
     * 充电金额
     */
    private BigDecimal chargeAmount;
    /**
     * 服务费金额
     */
    private BigDecimal serviceAmount;

    public void setChargeAmount(BigDecimal chargeAmount) {
        this.chargeAmount = chargeAmount.setScale(2, RoundingMode.DOWN);
    }

    public void setServiceAmount(BigDecimal serviceAmount) {
        this.serviceAmount = serviceAmount.setScale(2, RoundingMode.DOWN);
    }

    public void setChargeHour(BigDecimal chargeHour) {
        this.chargeTime = buildChargeTime(chargeHour);
    }
}
