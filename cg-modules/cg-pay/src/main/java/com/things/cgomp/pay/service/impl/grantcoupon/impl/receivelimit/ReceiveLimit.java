package com.things.cgomp.pay.service.impl.grantcoupon.impl.receivelimit;

import com.things.cgomp.pay.domain.CouponActivity;
import com.things.cgomp.pay.dto.coupon.ActivityCouponDTO;
import com.things.cgomp.pay.service.impl.grantcoupon.impl.SingleChargeCouponGrant;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

public abstract class ReceiveLimit {

    @Resource
    private ApplicationContext applicationContext;

    protected void grantCoupons(
            CouponActivity activity,
            Long userId,
            ActivityCouponDTO activityCoupon
    ) {
        SingleChargeCouponGrant couponGrant =  applicationContext.getBean(SingleChargeCouponGrant.class);;
        couponGrant.grant(
                activity,
                userId,
                activityCoupon
        );
    }


    public abstract void grantCoupons(
            CouponActivity activity,
            Long userId
    );

}
