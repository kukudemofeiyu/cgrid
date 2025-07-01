package com.things.cgomp.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.things.cgomp.common.core.enums.EnableEnum;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.api.enums.OrderStateShowEnum;
import com.things.cgomp.order.api.enums.PayStatusEnum;
import com.things.cgomp.order.api.domain.OrderDiscount;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@Accessors(chain = true)
public class OrderVo implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 订单状态（聚合而成）：1-进行中 2-已完成
     */
    private Integer orderState;

    /**
     * 站点id
     */
    private Long siteId;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 计费类型：0-按小时计费 1-按度数计费
     */
    private Integer billType;

    /**
     * 0-未支付 1-已支付 3-已退款
     */
    private Integer payStatus;

    /**
     * 退款状态：0-未退款 1-退款
     */
    private Integer refundStatus;

    /**
     * 异常状态：0-正常 1-异常
     */
    private Integer abnormalStatus;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 充电桩编号
     */
    private String pileSn;

    /**
     * 枪口名称
     */
    private String portName;

    /**
     * 实际充电时长（小时）
     */
    private BigDecimal realChargingTime;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 0-微信 1-卡支付 2-账户余额
     */
    private Integer payType;

    /**
     * 订单类型：0-充电订单 1-预约订单 2-占位订单
     */
    private Integer orderType;

    /**
     * 结束原因编码
     */
    private Integer endReasonCode;

    /**
     * 结束原因描述
     */
    private String endReasonDesc;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 运营商id
     */
    private Long operatorId;

    /**
     * 运营商名称
     */
    private String operatorName;

    /**
     * 实际充电开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime realStartTime;

    /**
     * 实际充电结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime realEndTime;

    /**
     * 处理流程状态：完成（0-处理完毕）未完成（1-未返积分 2-未数据统计 3-未分佣）
     */
    private Integer processStep;

    /**
     * 是否有占桩费: 0-否 1-是
     */
    private Integer ownOccupy;

    /**
     * 0-充电前占位 1-充电后占位
     */
    private Integer occupyType;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 子订单列表
     */
    private List<OrderInfo> childOrders;

    /**
     * 父订单
     */
    private OrderInfo parentOrder;

    /**
     * 订单优惠明细
     */
    private List<OrderDiscount> discounts;

    /**
     * 订单优惠金额
     */
    private BigDecimal discountAmount;

    public OrderVo setPayStatus(){
        payStatus = PayStatusEnum.buildPayStatus(
                refundStatus,
                payStatus
        );
        return this;
    }

    public OrderVo setChildOrders(List<OrderInfo> childOrders) {
        if(CollectionUtils.isEmpty(childOrders)){
            return this;
        }

        for (OrderInfo childOrder : childOrders) {
            childOrder.setOperatorId(operatorId)
                    .setOperatorName(operatorName);
        }

        this.childOrders = childOrders;

        return this;
    }

    public OrderVo setParentOrder(OrderInfo parentOrder) {
        if(Objects.isNull(parentOrder)){
            return this;
        }

        parentOrder.setOperatorId(operatorId)
                .setOperatorName(operatorName);

        this.parentOrder = parentOrder;
        return this;
    }

    public OrderVo setDiscountAmount(){
        discountAmount = buildDiscountAmount();
        return this;
    }

    public BigDecimal buildDiscountAmount(){
        if(CollectionUtils.isEmpty(discounts)){
            return BigDecimal.ZERO;
        }
        return discounts.stream()
                .map(OrderDiscount::getDiscountAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public OrderVo setOwnOccupy(){
        ownOccupy = buildOccupy();
        return this;
    }

    private Integer buildOccupy() {
        if(CollectionUtils.isEmpty(childOrders)){
            return EnableEnum.DISABLE.getCode();
        }

        return EnableEnum.ENABLE.getCode();
    }

    public OrderVo setOrderState(){
        orderState = OrderStateShowEnum.getOrderStateShow(
                orderState
        );
        return this;
    }
}
