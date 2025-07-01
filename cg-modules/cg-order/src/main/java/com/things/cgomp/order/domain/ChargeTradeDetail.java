package com.things.cgomp.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import com.things.cgomp.common.core.utils.MathUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单充电明细表
 * </p>
 *
 * @author baomidou
 * @since 2025-03-15
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("order_charge_trade_detail")
public class ChargeTradeDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 设备总单价
     */
    private BigDecimal deviceUnitPrice;

    /**
     * 平台电费单价
     */
    private BigDecimal ptUnitElec;

    /**
     * 平台服务费单价
     */
    private BigDecimal ptUnitService;

    /**
     * 平台总单价
     */
    private BigDecimal ptUnitPrice;

    /**
     * 电量
     */
    private BigDecimal energy;

    /**
     * 计损电量
     */
    private BigDecimal loseEnergy;

    /**
     * 金额
     */
    private BigDecimal deviceAmount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public ChargeTradeDetail setPtUnitPrice() {
        ptUnitPrice = MathUtil.sum(
                ptUnitService,
                ptUnitElec
        );

        return this;
    }

    public BigDecimal buildChargeFee() {
        BigDecimal ptUnitElecRate = buildPtUnitElecRate();
        return ptUnitElecRate.multiply(
                deviceAmount
        );
    }

    private BigDecimal buildPtUnitElecRate() {
        if (ptUnitPrice == null
                || ptUnitPrice.doubleValue() == 0
                || ptUnitElec == null
        ) {
            return new BigDecimal(1);
        }

        return ptUnitElec.divide(ptUnitPrice,
                4, RoundingMode.HALF_DOWN
        );
    }
}
