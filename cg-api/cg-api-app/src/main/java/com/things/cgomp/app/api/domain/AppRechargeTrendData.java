package com.things.cgomp.app.api.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author things
 */
@Data
@Accessors(chain = true)
public class AppRechargeTrendData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日期 yyyy-mm-dd
     */
    private String date;
    /**
     * 充值订单数
     */
    private Long rechargeCount;
    /**
     * 充值金额
     */
    private BigDecimal rechargeAmount;
}
