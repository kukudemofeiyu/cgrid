package com.things.cgomp.pay.service;

import com.things.cgomp.pay.domain.CouponActivityLimitDay;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;

/**
 * <p>
 * 优惠券活动限领次数（天）表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-04-10
 */
public interface ICouponActivityLimitDayService extends IService<CouponActivityLimitDay> {

    CouponActivityLimitDay selectLimit(
            Long userId,
            Long activityId,
            LocalDate date
    );

}
