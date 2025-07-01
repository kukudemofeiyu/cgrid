package com.things.cgomp.pay.vo.coupon;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 优惠券模板表
 * </p>
 *
 * @author baomidou
 * @since 2025-03-25
 */
@Getter
@Setter
@Accessors(chain = true)
public class CouponTemplateListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 运营商ID
     */
    private Long operatorId;

    /**
     * 使用时间类型(0-相对时间 1-绝对时间)
     */
    private Integer useTimeType;

    /**
     * 使用开始时间
     */
    private LocalDate startTime;

    /**
     * 使用结束时间
     */
    private LocalDate endTime;

    /**
     * 0-暂停使用 1-正常
     */
    private Integer status;

}
