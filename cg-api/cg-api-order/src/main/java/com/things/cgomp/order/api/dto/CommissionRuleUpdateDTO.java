package com.things.cgomp.order.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author things
 * @date 2025/3/4
 */
@Data
@Accessors(chain = true)
public class CommissionRuleUpdateDTO implements Serializable {

    /**
     * 规则ID
     */
    private Long id;
    /**
     * 分成比例
     */
    @NotNull(message = "分成比例不能为空")
    private BigDecimal ratio;
    /**
     * 分成类型
     * @see com.things.cgomp.common.core.enums.CommissionType
     */
    private Integer type;
    /**
     * 分成等级
     * @see com.things.cgomp.common.core.enums.CommissionLevel
     */
    private Integer level;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 运营商ID
     */
    private Long operatorId;
    /**
     * 站点ID
     */
    private Long siteId;
    /**
     * 状态
     */
    private Integer status;
}
