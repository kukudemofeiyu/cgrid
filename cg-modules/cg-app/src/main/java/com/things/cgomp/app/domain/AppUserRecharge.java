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
 * 充值记录表
 * @TableName app_user_recharge
 */
@TableName(value ="app_user_recharge")
@Data
public class AppUserRecharge implements Serializable {
    /**
     * 记录ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * IC卡号
     */
    private String cardNo;

    /**
     * 充值流水号
     */
    private String rechargeNumber;

    /**
     * 充值者手机号
     */
    private String chargeUserMobile;

    /**
     * 用户ID（被充值者）
     */
    private Long userId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 充值前余额
     */
    private BigDecimal beforeBalance;

    /**
     * dang'q
     */
    private Integer balance;

    /**
     * 充值渠道（0前台充值 1后台充值）
     */
    private Integer rechargeChannel;

    /**
     * 类型（0充值 1消费 2赠送 3系统充值 4退款 5退款失败 6转账 7订单退款 8提现 9扣除赠送）
     */
    private Integer type;

    /**
     * 状态（0正常  1失败）
     */
    private String status;

    /**
     * 充值时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}