package com.things.cgomp.order.api.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.things.cgomp.common.core.enums.CommissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分成记录表 order_commission_record
 *
 * @author things
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("order_commission_record")
public class OrderCommissionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 运营商ID
     */
    private Long operatorId;
    /**
     * 分成规则ID
     */
    private Long ruleId;
    /**
     * 分成类型
     * {@link CommissionType}
     */
    private Integer commissionType;
    /**
     * 分成类型描述
     */
    @TableField(exist = false)
    private String commissionTypeDesc;
    /**
     * 平台分成比例
     */
    private BigDecimal platformPercent;
    /**
     * 运营商分成比例
     */
    private BigDecimal operatorPercent;
    /**
     * 平台分成金额
     */
    private BigDecimal platformAmount;
    /**
     * 运营商分成金额
     */
    private BigDecimal operatorAmount;
    /**
     * 运营商实际分成金额
     */
    private BigDecimal operatorRealAmount;
    /**
     * 发生时间（目前对应订单支付时间）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eventTime;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
