package com.things.cgomp.order.api.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author things
 */
@Data
@Accessors(chain = true)
public class OrderTrendData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 充电次数
     */
    private Long chargeCount;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 充电量
     */
    private BigDecimal consumeElectricity;
    /**
     * 充电时长（小时）
     */
    private BigDecimal chargeTime;
}
