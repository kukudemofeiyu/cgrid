package com.things.cgomp.pay.mapper;

import com.things.cgomp.pay.domain.CouponActivityUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 优惠券活动-用户表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-03-25
 */
public interface CouponActivityUserMapper extends BaseMapper<CouponActivityUser> {

    List<Long> selectUserIds(
           @Param("activityId") Long activityId
    );

    Integer deleteUserIds(
            @Param("activityId") Long activityId
    );
}
