package com.things.cgomp.pay.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 优惠券活动限领次数（总）表
 * </p>
 *
 * @author baomidou
 * @since 2025-04-10
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("pay_coupon_activity_limit_total")
public class CouponActivityLimitTotal implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 已领次数
     */
    private Integer number;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 版本号
     */
    @Version
    private Integer version;
}
