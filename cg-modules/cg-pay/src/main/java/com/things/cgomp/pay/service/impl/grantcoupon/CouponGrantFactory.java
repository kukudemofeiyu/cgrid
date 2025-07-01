package com.things.cgomp.pay.service.impl.grantcoupon;

import com.things.cgomp.pay.enums.CouponActivityTypeEnum;
import com.things.cgomp.pay.service.impl.grantcoupon.impl.FirstChargeCouponGrant;
import com.things.cgomp.pay.service.impl.grantcoupon.impl.InternalCouponGrant;
import com.things.cgomp.pay.service.impl.grantcoupon.impl.SingleChargeCouponGrant;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CouponGrantFactory {

    @Resource
    private ApplicationContext applicationContext;


    public CouponGrant createCouponGrant(Integer activityType){
        CouponActivityTypeEnum activityTypeEnum = CouponActivityTypeEnum.getEnum(activityType);
        if(activityTypeEnum == null){
            return null;
        }

        CouponGrant couponGrant = null;
        switch (activityTypeEnum){
            case FIRST_CHARGE:
                couponGrant=applicationContext.getBean(FirstChargeCouponGrant.class);
                break;
            case SINGLE_CHARGE:
                couponGrant=applicationContext.getBean(SingleChargeCouponGrant.class);
                break;
            case INTERNAL_COUPON:
                couponGrant=applicationContext.getBean(InternalCouponGrant.class);
                break;
        }

        return couponGrant;

    }
}
