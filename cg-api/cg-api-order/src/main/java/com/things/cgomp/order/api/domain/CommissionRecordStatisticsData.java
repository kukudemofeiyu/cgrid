package com.things.cgomp.order.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单分成记录统计数据
 * @author things
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CommissionRecordStatisticsData implements Serializable {

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
     * 运营商手机号
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
     * 数据日期
     * 月份/年份
     */
    private String date;
    /**
     * 总分成金额
     */
    private BigDecimal totalAmount;
    /**
     * 运营商分成金额
     */
    private BigDecimal operatorAmount;
    /**
     * 平台分成金额
     */
    private BigDecimal platformAmount;
}
