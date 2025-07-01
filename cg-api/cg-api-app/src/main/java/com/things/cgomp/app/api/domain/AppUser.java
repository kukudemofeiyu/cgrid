package com.things.cgomp.app.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 注册用户表（微信小程序用户）
 * app_user
 */
@Data
@Accessors(chain = true)
@TableName(value ="app_user")
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 微信openID
     */
    private String wxOpenId;

    /**
     * 微信unionID
     */
    private String wxUnionId;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 状态（1正常 0停用）
     */
    private Integer status;
    /**
     * 支付方式ID
     */
    private Long paymentTypeId;

    /**
     * 首次充电:0-否 1-是
     */
    private Integer firstCharge;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 关键字，搜索时使用
     */
    @TableField(exist = false)
    private String keyword;

    /**
     * 账户余额
     */
    @TableField(exist = false)
    private BigDecimal balance;
    /**
     * IC卡余额
     */
    @TableField(exist = false)
    private BigDecimal cardBalance;
    /**
     * 总余额
     */
    @TableField(exist = false)
    private BigDecimal totalBalance;
}