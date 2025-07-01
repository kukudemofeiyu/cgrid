package com.things.cgomp.order.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.things.cgomp.common.core.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 充电订单表
 * </p>
 *
 * @author baomidou
 * @since 2025-03-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("order_info")
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    private String sn;

    /**
     * 交易流水号
     */
    private String tradeSn;

    /**
     * 订单类型：0-充电订单 1-预约订单 2-占位订单
     */
    private Integer orderType;

    /**
     * 订单状态：1.充电中 2.结束充电 3.交易记录确认 4.已拔枪
     */
    private Integer orderState;

    /**
     * 订单来源：1-微信小程序，2-H5，3-卡
     */
    private Integer orderSource;

    /**
     * 计费类型，0-按小时计费（取决于用户选择,默认） 1-按度数计费（取决于 用户选择）
     */
    private Integer billType;

    /**
     * 异常状态：0-正常 1-异常
     */
    private Integer abnormalStatus;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 充电桩ID
     */
    private Long pileId;

    /**
     * 充电口ID
     */
    private Long portId;

    /**
     * 预约开始时间
     */
    private LocalDateTime appointStartTime;

    /**
     * 预约结束时间
     */
    private LocalDateTime appointEndTime;

    /**
     * 预约充电时长（小时）
     */
    private BigDecimal appointChargingTime;

    /**
     * 实际充电开始时间
     */
    private LocalDateTime realStartTime;

    /**
     * 实际充电结束时间
     */
    private LocalDateTime realEndTime;

    /**
     * 实际充电时长（小时）
     */
    private BigDecimal realChargingTime;

    /**
     * 结束原因编码
     */
    private Integer endReasonCode;

    /**
     * 结束原因描述
     */
    private String endReasonDesc;

    /**
     * 0-不收费 1-收费（默认） 取决于充电桩
     */
    private Integer isFee;

    /**
     * 设备类型 2:二轮车 4:四轮车
     */
    private Integer deviceType;

    /**
     * 耗电量 单位 kWh
     */
    private BigDecimal consumeElectricity;

    /**
     * 计费规则id
     */
    private Long payRuleId;

    /**
     * 计费模型编号（1-9999）
     */
    private Integer payModelId;

    /**
     * 对应于app_user主键
     */
    private Long userId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 车牌号码
     */
    private String licensePlateNumber;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 充电费用
     */
    private BigDecimal chargeFee;

    /**
     * 服务费
     */
    private BigDecimal serviceFee;

    /**
     * 电费优惠金额
     */
    private BigDecimal chargeDiscountFee;

    /**
     * 服务费优惠金额
     */
    private BigDecimal serviceDiscountFee;

    /**
     * 优惠券优惠金额
     */
    private BigDecimal couponAmount;

    /**
     * 站点优惠金额
     */
    private BigDecimal siteDiscountAmount;

    /**
     * 0-微信 1-卡支付 2-账户余额
     */
    private Integer payType;

    /**
     * 0-未支付 1-已支付
     */
    private Integer payStatus;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 退款状态：0-未退款 1-退款
     */
    private Integer refundStatus;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 退款时间
     */
    private LocalDateTime refundTime;

    /**
     * 卡号(卡支付时插入)
     */
    private String cardNo;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 支付订单id
     */
    private String payOrderId;

    /**
     * 订单创建时间
     */
    private LocalDateTime createTime;

    /**
     * 站点id
     */
    private Long siteId;

    /**
     * 是否为团体订单：0-否 1-是
     */
    private Integer groupOrder;

    /**
     * 团体卡号
     */
    private String groupCardNo;

    @Version
    private Long version;

    /**
     * VIN码
     */
    private String vin;

    /**
     * 插枪时间
     */
    private LocalDateTime insertTime;

    /**
     * 交易记录确认时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime settlementTime;

    /**
     * 拔枪时间
     */
    private LocalDateTime drawGunTime;

    /**
     * 是否欠款：0-否 1-是
     */
    private Integer debtStatus;

    /**
     * 欠款金额
     */
    private BigDecimal debtAmount;

    /**
     * 流程缺失：0-否 1-是
     */
    private Integer processLoss;

    /**
     * 处理流程状态：完成（0-处理完毕）未完成（1-未返积分 2-未数据统计 3-未分佣） 
     */
    private Integer processStep;

    /**
     * 0-充电前占位 1-充电后占位
     */
    private Integer occupyType;

    /**
     * 运营商id
     */
    @TableField(exist = false)
    private Long operatorId;

    /**
     * 运营商名称
     */
    @TableField(exist = false)
    private String operatorName;

    public List<Long> buildDeviceIds(){
        List<Long> deviceIds = new ArrayList<>();
        if(pileId != null){
            deviceIds.add(pileId);
        }

        if(portId != null){
            deviceIds.add(portId);
        }

        return deviceIds;
    }

    public OrderInfo setRealChargingTime(){
        this.realChargingTime = DateUtils.calIntervalTime(
                realStartTime,
                realEndTime
        );
        return this;
    }

    public OrderInfo setServiceFee() {
        this.serviceFee = buildServiceFee();
        return this;
    }

    private BigDecimal buildServiceFee() {
        BigDecimal serviceFee = payAmount.subtract(chargeFee)
                .setScale(2, RoundingMode.HALF_UP);
        if (serviceFee.doubleValue() < 0) {
            return BigDecimal.valueOf(0);
        }
        return serviceFee;
    }

}
