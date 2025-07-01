package com.things.cgomp.order.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderAppVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 订单类型：0-设备订单 1-预约订单 2-占位订单
     */
    private Integer orderType;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 交易流水号
     */
    private String tradeSn;
    /**
     * 站点
     */
    private String siteName;
    /**
     * 端口ID
      */
    private Long portId;
    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;
    /**
     * 金额
     */
    private Double amount;
    /**
     * 电量
     */
    private Double energy;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 充电时长
     */
    private BigDecimal chargingTime;
    /**
     * 订单状态1-未完成 2-已完成 3待支付
     */
    private Integer status;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 支付金额
     */
    private BigDecimal payAmount;


}
