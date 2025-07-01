package com.things.cgomp.app.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 充值订单表
 * @TableName app_recharge_order
 */
@TableName(value ="app_recharge_order")
@Data
public class AppRechargeOrder implements Serializable {
    /**
     * 订单ID
     */
    @TableId
    private String id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单状态：0-待支付，1-支付成功，2-支付失败, 3-已退款
     */
    private Integer status;

    /**
     * 订单来源：1-微信，2-支付宝，3-抖音 4-系统
     */
    private Integer type;

    /**
     * 充值金额
     */
    private BigDecimal amount;
    /**
     * 支付金额
     */
    private BigDecimal payAmount;
    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;


    /**
     * 第三方支付交易订单号
     */
    private String thirdPartyOrderId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 公众账号ID
     */
    private String appid;
    /**
     * 商户号
     */
    private String mchid;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}