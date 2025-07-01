package com.things.cgomp.order.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单分成记录数据
 * @author things
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CommissionRecordData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 订单创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;
    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;
    /**
     * 站点名称
     */
    private String siteName;
    /**
     * 充电桩名称
     */
    private String pileName;
    /**
     * 充电桩编号
     */
    private String pileSn;
    /**
     * 运营商ID
     */
    private String operatorId;
    /**
     * 运营商名称
     */
    private String operatorName;
    /**
     * 分成类型
     * {@link com.things.cgomp.common.core.enums.CommissionType}
     */
    private Integer commissionType;
    /**
     * 平台分成金额
     */
    private BigDecimal platformAmount;
    /**
     * 平台分成比例
     */
    private BigDecimal platformPercent;
    /**
     * 运营商分成金额
     */
    private BigDecimal operatorAmount;
    /**
     * 运营商分成比例
     */
    private BigDecimal operatorPercent;
}
