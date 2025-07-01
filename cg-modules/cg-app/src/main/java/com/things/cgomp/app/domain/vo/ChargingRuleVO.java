package com.things.cgomp.app.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChargingRuleVO {
    /**
     * 尖峰平谷类型（0-尖 1峰 2平 3谷）
     */
    private Integer type;
    /**
     * 时段开始时间
     */
    private String startTime;

    /**
     * 时段结束时间
     */
    private String endTime;
    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 电费
     */
    private BigDecimal electrovalence;

    /**
     * 服务费
     */
    private BigDecimal serviceCharge;
}
