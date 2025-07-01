package com.things.cgomp.pay.mapper;

import com.things.cgomp.pay.domain.CouponActivityLimitDay;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

/**
 * <p>
 * 优惠券活动限领次数（天）表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-04-10
 */
public interface CouponActivityLimitDayMapper extends BaseMapper<CouponActivityLimitDay> {

    CouponActivityLimitDay selectLimit(
           @Param("userId") Long userId,
           @Param("activityId") Long activityId,
           @Param("date") LocalDate date
    );
}
