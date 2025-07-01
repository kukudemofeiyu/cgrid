package com.things.cgomp.pay.service.impl;

import com.things.cgomp.pay.domain.CouponActivityUser;
import com.things.cgomp.pay.mapper.CouponActivityUserMapper;
import com.things.cgomp.pay.service.ICouponActivityUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 优惠券活动-用户表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-25
 */
@Service
public class CouponActivityUserServiceImpl extends ServiceImpl<CouponActivityUserMapper, CouponActivityUser>
        implements ICouponActivityUserService {

    @Override
    public void saveUserIds(
            Long activityId,
            List<Long> userIds
    ) {

        if(CollectionUtils.isEmpty(userIds)){
            return;
        }

        userIds.stream()
                .map(userId -> new CouponActivityUser(
                        activityId,
                        userId
                ))
                .forEach(baseMapper::insert);

    }

    @Override
    public List<Long> selectUserIds(
            Long activityId
    ) {
        return baseMapper.selectUserIds(
                activityId
        );
    }

    @Override
    public void updateUserIds(
            Long activityId,
            List<Long> userIds
    ) {
        baseMapper.deleteUserIds(activityId);

        saveUserIds(
                activityId,
                userIds
        );
    }
}
