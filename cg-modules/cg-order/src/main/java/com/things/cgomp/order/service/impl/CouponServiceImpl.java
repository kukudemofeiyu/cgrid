package com.things.cgomp.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.order.mapper.CouponMapper;
import com.things.cgomp.order.service.ICouponService;
import com.things.cgomp.pay.api.domain.Coupon;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 优惠券表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-04-02
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements ICouponService {

}
