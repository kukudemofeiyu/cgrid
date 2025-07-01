package com.things.cgomp.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.things.cgomp.pay.domain.CouponActivityUser;

import java.util.List;

/**
 * <p>
 * 优惠券活动-用户表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-25
 */
public interface ICouponActivityUserService extends IService<CouponActivityUser> {
    void saveUserIds(
            Long activityId,
            List<Long> userIds
    );

    List<Long> selectUserIds(
            Long activityId
    );

    void updateUserIds(
            Long activityId,
            List<Long> userIds
    );

}
