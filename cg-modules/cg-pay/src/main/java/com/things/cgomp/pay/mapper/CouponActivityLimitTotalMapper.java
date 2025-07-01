package com.things.cgomp.pay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.pay.domain.CouponActivityLimitTotal;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 优惠券活动限领次数（总）表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-04-10
 */
public interface CouponActivityLimitTotalMapper extends BaseMapper<CouponActivityLimitTotal> {

    CouponActivityLimitTotal selectLimit(
            @Param("userId") Long userId,
            @Param("activityId") Long activityId
    );

}
