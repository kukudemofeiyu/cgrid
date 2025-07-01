package com.things.cgomp.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.pay.domain.CouponActivityLimitTotal;
import com.things.cgomp.pay.mapper.CouponActivityLimitTotalMapper;
import com.things.cgomp.pay.service.ICouponActivityLimitTotalService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 优惠券活动限领次数（总）表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-04-10
 */
@Service
public class CouponActivityLimitTotalServiceImpl
        extends ServiceImpl<CouponActivityLimitTotalMapper, CouponActivityLimitTotal>
        implements ICouponActivityLimitTotalService {

    @Override
    public CouponActivityLimitTotal selectLimit(
            Long userId,
            Long activityId
    ) {
        return baseMapper.selectLimit(
                userId,
                activityId
        );
    }
}
