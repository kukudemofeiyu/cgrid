package com.things.cgomp.pay.service.impl;

import com.things.cgomp.pay.domain.CouponActivityLimitDay;
import com.things.cgomp.pay.mapper.CouponActivityLimitDayMapper;
import com.things.cgomp.pay.service.ICouponActivityLimitDayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * <p>
 * 优惠券活动限领次数（天）表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-04-10
 */
@Service
public class CouponActivityLimitDayServiceImpl extends ServiceImpl<CouponActivityLimitDayMapper, CouponActivityLimitDay>
        implements ICouponActivityLimitDayService {

    @Override
    public CouponActivityLimitDay selectLimit(
            Long userId,
            Long activityId,
            LocalDate date
    ) {
        return baseMapper.selectLimit(
                userId,
                activityId,
                date
        );
    }
}
