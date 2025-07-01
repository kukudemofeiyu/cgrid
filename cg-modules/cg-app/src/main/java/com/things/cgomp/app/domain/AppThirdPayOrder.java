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
 * 第三方支付订单表
 * @TableName app_third_pay_order
 */
@TableName(value ="app_third_pay_order")
@Data
public class AppThirdPayOrder implements Serializable {
    /**
     * 订单ID
     */
    private String id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单状态：0-待支付，1-支付成功，2-支付失败
     */
    private Integer status;

    /**
     * 订单来源：1-微信，2-支付宝，3-抖音
     */
    private Integer orderSource;

    /**
     * 金额
     */
    private Integer amount;

    /**
     * 第三方订单号
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
    /**
     * 附加数据
     */
    private String attach;
    /**
     * 充电订单集合
     */
    private String chargeOrderSn;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}