package com.things.cgomp.pay.service.impl.grantcoupon.impl;

import com.things.cgomp.pay.domain.CouponActivity;
import com.things.cgomp.pay.dto.coupon.CouponActivityConfigDTO;
import com.things.cgomp.pay.service.impl.grantcoupon.CouponGrant;
import com.things.cgomp.pay.service.impl.grantcoupon.impl.receivelimit.ReceiveLimit;
import com.things.cgomp.pay.service.impl.grantcoupon.impl.receivelimit.ReceiveLimitFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SingleChargeCouponGrant extends CouponGrant {

    @Resource
    private ReceiveLimitFactory receiveLimitFactory;

    @Override
    public void grant(
            CouponActivity activity,
            Long userId
    ) {
        CouponActivityConfigDTO activityConfig = activity.getActivityConfig();
        ReceiveLimit receiveLimit = receiveLimitFactory.createReceiveLimit(activityConfig.getReceiveLimitType());
        if(receiveLimit != null){
            receiveLimit.grantCoupons(
                    activity,
                    userId
            );
        }

    }
}
