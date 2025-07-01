package com.things.cgomp.order.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author things
 * @date 2025/3/4
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CommissionRuleQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 规则等级
     * @see com.things.cgomp.common.core.enums.CommissionLevel
     */
    private Integer level;

    private Integer pageNum;

    private Integer pageSize;
}
