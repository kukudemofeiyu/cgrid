package com.things.cgomp.order.api.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author things
 */
@Data
@Accessors(chain = true)
public class OrderFinanceTotalData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日总销售额
     */
    private BigDecimal daySalesTotal;
    /**
     * 月总销售额
     */
    private BigDecimal monthSalesTotal;
    /**
     * 年总销售额
     */
    private BigDecimal yearSalesTotal;
}
