package com.things.cgomp.pay.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 优惠券活动模板表
 * </p>
 *
 * @author baomidou
 * @since 2025-04-09
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("pay_coupon_activity_template")
@NoArgsConstructor
public class CouponActivityTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 模板id
     */
    private Long templateId;

    public CouponActivityTemplate(Long activityId, Long templateId) {
        this.activityId = activityId;
        this.templateId = templateId;
    }
}
