package com.things.cgomp.system.api.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 小程序账单明细
 */
@Data
public class AppRechargeRecordVO {
    private Long id;
    /**
     * 订单号
     *
     */
    private String serialNumber;
    /**
     * 发生金额
     */
    private BigDecimal amount;
    /**
     * 记录类型 1用户充值 2订单支付 3提现 4系统充值 5订单退款 6转账 7 退款
     */
    private Integer type;
    /**
     * 记录状态 1成功 2失败 3处理中
     */
    private Integer status;
    /**
     * 创建时间
     */
    private String eventTime;

}
