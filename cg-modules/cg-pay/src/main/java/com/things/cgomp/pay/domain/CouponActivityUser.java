package com.things.cgomp.pay.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 优惠券活动-用户表
 * </p>
 *
 * @author baomidou
 * @since 2025-03-25
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("pay_coupon_activity_user")
@NoArgsConstructor
public class CouponActivityUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 用户id
     */
    private Long userId;

    public CouponActivityUser(
            Long activityId,
            Long userId
    ) {
        this.activityId = activityId;
        this.userId = userId;
    }
}
