package com.things.cgomp.order.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.things.cgomp.order.api.domain.OrderDiscount;
import com.things.cgomp.order.api.domain.OrderLog;
import com.things.cgomp.order.api.enums.OrderStateShowEnum;
import com.things.cgomp.order.api.enums.PayStatusEnum;
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
public class OrderDetailVo implements Serializable {

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
     * 订单类型：0-充电订单 1-预约订单 2-占位订单
     */
    private Integer orderType;

    /**
     * 订单状态（聚合而成）：1-进行中 2-已完成
     */
    private Integer orderState;

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
     * 订单优惠明细
     */
    private List<OrderDiscount> discounts;

    /**
     * 订单优惠金额
     */
    private BigDecimal discountAmount;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refundTime;

    /**
     * 站点地址
     */
    private String siteAddress;

    /**
     * 充电桩id
     */
    private Long pileId;

    /**
     * 充电桩编号
     */
    private String pileSn;

    /**
     * 充电桩名称
     */
    private String pileName;

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
     * 实际充电时长（小时）
     */
    private BigDecimal realChargingTime;

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
     * 耗电量 单位 kWh
     */
    private BigDecimal consumeElectricity;

    /**
     * 对应于app_user主键
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 车牌号码
     */
    private String licensePlateNumber;

    /**
     * 0-微信 1-卡支付 2-账户余额
     */
    private Integer payType;

    /**
     * 0-未支付 1-已支付 3-已退款
     */
    private Integer payStatus;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    /**
     * 是否为团体订单：0-否 1-是
     */
    private Integer groupOrder;

    /**
     * 团体卡号
     */
    private String groupCardNo;

    /**
     * 处理流程状态：完成（0-处理完毕）未完成（1-未返积分 2-未数据统计 3-未分佣） 
     */
    private Integer processStep;

    /**
     * 计费规则id
     */
    private Long payRuleId;

    /**
     * 计费模型编号（1-9999）
     */
    private Integer payModelId;

    /**
     * 结束原因编码
     */
    private Integer endReasonCode;
    /**
     * 结束原因描述
     */
    private String endReasonDesc;

    private List<OrderPayRuleVo> payRules;

    private List<OrderLog> orderLogs;

    public OrderDetailVo setPayStatus(){
        payStatus = PayStatusEnum.buildPayStatus(
                refundStatus,
                payStatus
        );
        return this;
    }

    public OrderDetailVo setOrderState(){
        orderState = OrderStateShowEnum.getOrderStateShow(
                orderState
        );
        return this;
    }

    public OrderDetailVo setDiscountAmount(){
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

}
