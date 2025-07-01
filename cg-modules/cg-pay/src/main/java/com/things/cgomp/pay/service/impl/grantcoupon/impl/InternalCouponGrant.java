package com.things.cgomp.pay.service.impl.grantcoupon.impl;

import com.things.cgomp.pay.domain.CouponActivity;
import com.things.cgomp.pay.service.impl.grantcoupon.CouponGrant;
import org.springframework.stereotype.Component;

@Component
public class InternalCouponGrant extends CouponGrant {

    @Override
    public void grant(
            CouponActivity activity,
            Long userId
    ) {
        activity.buildCoupons()
                .forEach(activityCoupon -> grant(
                        activity,
                        userId,
                        activityCoupon
                ));
    }

}
