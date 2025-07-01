package com.things.cgomp.system.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class AppRechargeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 充值金额
     */
    private BigDecimal amount;
    /**
     * 订单号
     */
    private String orderNo;
}
