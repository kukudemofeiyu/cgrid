package com.things.cgomp.app.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawOrRechargeDTO {
    /**
     * 金额
     */
    private BigDecimal amount;
}
