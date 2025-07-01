package com.things.cgomp.order.api.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author things
 */
@Data
@Accessors(chain = true)
public class OrderStatisticsData  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**-----------设备、站点相关属性 ----------------/
    /**
     * 站点ID
     */
    private Long siteId;
    /**
     * 站点名称
     */
    private String siteName;
    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备编号
     */
    private String deviceSn;
    /**
     * 运行状态
     * 1在线 0离线
     */
    private Integer runStatus;

    /**-----------费用相关属性 ----------------/
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 电费
     */
    private BigDecimal chargeFee;
    /**
     * 服务费
     */
    private BigDecimal serviceFee;
    /**
     * 订单数量
     */
    private Long orderNum;
    /**
     * 充电次数
     */
    private Long chargeCount;
    /**
     * 充电时长（小时）
     */
    private BigDecimal chargeTime;
    /**
     * 总耗电量
     */
    private BigDecimal consumeElectricity;
    /**
     * 总收益
     */
    private BigDecimal totalIncome;
    /**
     * 实际收益
     */
    private BigDecimal realIncome;
    /**
     * 分成金额
     */
    private BigDecimal commissionAmount;
    /**
     * 退款金额
     */
    private BigDecimal refundAmount;
    /**
     * 支付金额
     */
    private BigDecimal payAmount;
}
