package com.things.cgomp.order.api.vo;

import com.things.cgomp.common.core.utils.MathUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@Accessors(chain = true)
public class OrderPayRuleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 尖峰平谷类型（0-尖 1峰 2平 3谷）
     */
    private Integer type;

    /**
     * 电费
     */
    private BigDecimal electrovalence;

    /**
     * 服务费
     */
    private BigDecimal serviceCharge;

    /**
     * 总费用
     */
    private BigDecimal totalCost;

    /**
     * 时段开始时间
     */
    private String startTime;

    /**
     * 时段结束时间
     */
    private String endTime;

    public OrderPayRuleVo setTotalCost(){
        this.totalCost = buildTotalCost();

        return this;
    }

    private BigDecimal buildTotalCost() {
        return MathUtil.sum(
                electrovalence,
                serviceCharge
        );
    }
}
