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
public class AppUserRefundReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 充值记录ID
     * 根据充值记录进行退款
     */
    @NotNull(message = "充值记录不能为空")
    private Long id;
    /**
     * 退款金额
     */
    @NotNull(message = "退款金额不能为空")
    private BigDecimal amount;
}
