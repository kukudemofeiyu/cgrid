package com.things.cgomp.order.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@Accessors(chain = true)
public class CouponDiscountVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 优惠券id
     */
    private Long couponId;

    /**
     * 模板idid
     */
    private Long templateId;

    /**
     * 优惠券类型：0-现金券 1-折扣券
     */
    private Integer couponType;

    /**
     * 面额
     */
    private BigDecimal faceValue;

    /**
     * 折扣比例(%)
     */
    private Integer rate;

    /**
     * 可抵扣费用(0-服务费 1-总费用)
     */
    private Integer deductionType;

    /**
     * 满足总费用额度
     */
    private BigDecimal feeLimit;

}
