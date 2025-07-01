package com.things.cgomp.system.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AppRechargeOrderVO {
    //订单号
    private String sn;
    //支付方式 (1-微信，4-系统)
    private Integer type;
    //充值金额
    private BigDecimal amount;
    /**
     * 支付金额
     */
    private BigDecimal payAmount;
    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;
    //支付状态 (0-待支付，1-支付成功，2-支付失败, 3-已退款)
    private Integer status;
    //手机号
    private String phone;
    //用户名
    private String username;
    //创建时间
    private String createTime;
    //支付时间
    private String payTime;



}
