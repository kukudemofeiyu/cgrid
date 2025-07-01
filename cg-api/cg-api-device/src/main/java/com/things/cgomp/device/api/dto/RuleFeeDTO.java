package com.things.cgomp.device.api.dto;

import com.things.cgomp.common.core.utils.MathUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
public class RuleFeeDTO implements Serializable {
    /**
     *  ruleId
     */
    private Long ruleId;

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

    public RuleFeeDTO format(){
        electrovalence = MathUtil.format(electrovalence);
        serviceCharge = MathUtil.format(serviceCharge);
        return this;
    }

}
