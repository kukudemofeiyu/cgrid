package com.things.cgomp.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.things.cgomp.pay.domain.CouponActivityLimitTotal;

/**
 * <p>
 * 优惠券活动限领次数（总）表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-04-10
 */
public interface ICouponActivityLimitTotalService extends IService<CouponActivityLimitTotal> {

    CouponActivityLimitTotal selectLimit(
            Long userId,
            Long activityId
    );

}
