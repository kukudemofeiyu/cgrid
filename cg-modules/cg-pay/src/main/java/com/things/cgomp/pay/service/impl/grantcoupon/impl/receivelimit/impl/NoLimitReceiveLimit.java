package com.things.cgomp.pay.service.impl.grantcoupon.impl.receivelimit.impl;

import com.things.cgomp.pay.domain.CouponActivity;
import com.things.cgomp.pay.service.impl.grantcoupon.impl.receivelimit.ReceiveLimit;
import org.springframework.stereotype.Component;

@Component
public class NoLimitReceiveLimit extends ReceiveLimit {

    @Override
    public void grantCoupons(
            CouponActivity activity,
            Long userId
    ) {
        activity.buildCoupons()
                .forEach(activityCoupon -> grantCoupons(
                        activity,
                        userId,
                        activityCoupon
                ));
    }

}
