package com.things.cgomp.order.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.things.cgomp.order.api.domain.OrderDiscount;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Accessors(chain = true)
public class OrderAppDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 站点名称
     */
    private String siteName;
    /**
     * 地址信息
     */
    private String address;
    /**
     * 经度
     */
    private float lng;

    /**
     * 维度
     */
    private float lat;
    /**
     * 订单类型：0-设备订单 1-预约订单 2-占位订单
     */
    private Integer orderType;


    /**
     * 实际充电时长（小时）
     */
    private BigDecimal realChargingTime;
    /**
     * 耗电量 单位 kWh
     */
    private BigDecimal consumeElectricity;
    /**
     * 订单号
     */
    private String sn;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    /**
     * 是否为团体订单：0-否 1-是
     */
    private Integer groupOrder;
    /**
     * 桩编号
     */
    private String pileSn;

    /**
     * 充电类型，0-快充，1-慢充
     */
    private Integer chargeType;

    /**
     * 枪口id
     */
    private Long portId;

    /**
     * 枪口名称
     */
    private String portName;
    /**
     * 额定充电电流（A）
     */
    private String electric;

    /**
     * 额定充电电压（V）
     */
    private String voltage;

    /**
     *最大功率（kW）
     */
    private String maxPower;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 充电费用
     */
    private BigDecimal chargeFee;

    /**
     * 服务费
     */
    private BigDecimal serviceFee;

    /**
     * 0-微信 1-卡支付 2-账户余额
     */
    private Integer payType;
    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;
    /**
     * 订单状态1-未完成 2-已完成 3待支付
     */
    private Integer status;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 占位费（充电前）
     */
    private BigDecimal occupyFee;
    private Integer occupyFeePayStatus;
    private Long occupyFeeId;
    /**
     * 占位费（充电后）
     */
    private BigDecimal occupyFeeAfter;
    private Integer occupyFeeAfterPayStatus;
    private Long occupyFeeAfterId;
    /**
     * 订单优惠明细
     */
    private List<OrderDiscount> discounts;

    /**
     * 订单优惠金额
     */
    private BigDecimal discountAmount;
    /**
     * 是否退款：0-未退款 1-退款
     */
    private Integer isRefund;
    /**
     * 结束原因
     */
    private String endReason;
    /**
     * 是否有占桩费0-没有 1-有
     */
    private Integer isOccupyFee;

}
