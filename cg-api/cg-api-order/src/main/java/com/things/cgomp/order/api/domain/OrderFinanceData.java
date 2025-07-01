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
public class OrderFinanceData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 运营商ID
     */
    private Long operatorId;
    /**
     * 运营商名称
     */
    private String operatorName;
    /**
     * 运营商手机号码
     */
    private String mobile;
    /**
     * 站点ID
     */
    private Long siteId;
    /**
     * 站点名称
     */
    private String siteName;
    /**
     * 充电桩ID
     */
    private Long pileId;
    /**
     * 充电桩编号
     */
    private String pileSn;
    /**
     * 日销售额
     */
    private BigDecimal daySales;
    /**
     * 月销售额
     */
    private BigDecimal monthSales;
    /**
     * 年销售额
     */
    private BigDecimal yearSales;
}
