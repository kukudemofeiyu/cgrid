package com.things.cgomp.system.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class CommissionRuleAdd implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分成者用户ID
     */
    @NotNull(message = "分成者不能为空")
    private Long userId;
    /**
     * 站点ID
     */
    @NotNull(message = "站点ID不能为空")
    private Long siteId;
    /**
     * 分成比例
     */
    @NotNull(message = "分成比例不能为空")
    private BigDecimal ratio;
}
