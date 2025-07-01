package com.things.cgomp.system.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author things
 * @date 2025/3/10
 */
@Data
@Accessors(chain = true)
public class AppUserRechargeReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @NotNull(message = "用户不能为空")
    private Long userId;
    /**
     * 充值金额
     */
    @NotNull(message = "充值金额不能为空")
    private BigDecimal amount;
}
