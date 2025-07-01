package com.things.cgomp.app.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 提现记录表
 * @TableName app_user_refund
 */
@TableName(value ="app_user_refund")
@Data
public class AppUserRefund implements Serializable {
    /**
     * 记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 赠送扣除（待确认具体业务）
     */
    private BigDecimal giftAmount;

    /**
     * 提现前余额
     */
    private BigDecimal beforeBalance;

    /**
     * 当前余额
     */
    private BigDecimal balance;

    /**
     * 提现状态（0待审核 1审核中 2已提现 3提现失败）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 申请时间
     */
    private Date requestTime;

    /**
     * 处理人
     */
    private Long handleBy;

    /**
     * 处理时间
     */
    private Date handleTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}