package com.things.cgomp.pay.service.impl.grantcoupon.impl.receivelimit.impl;

import com.things.cgomp.pay.domain.CouponActivity;
import com.things.cgomp.pay.domain.CouponActivityLimitTotal;
import com.things.cgomp.pay.service.ICouponActivityLimitTotalService;
import com.things.cgomp.pay.service.impl.grantcoupon.impl.receivelimit.ReceiveLimit;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TotalReceiveLimit extends ReceiveLimit {

    @Resource
    private ICouponActivityLimitTotalService limitTotalService;

    @Override
    public void grantCoupons(
            CouponActivity activity,
            Long userId
    ) {
        Integer receiveLimit = activity.buildReceiveLimit();
        if(receiveLimit == null){
            return;
        }

        CouponActivityLimitTotal limitTotal = limitTotalService.selectLimit(
                userId,
                activity.getId()
        );

        if(limitTotal != null && limitTotal.getNumber() >= receiveLimit){
            return;
        }

        activity.buildCoupons()
                .forEach(activityCoupon -> grantCoupons(
                        activity,
                        userId,
                        activityCoupon
                ));

        saveLimitTotal(
                limitTotal,
                activity,
                userId
        );

    }

    private void saveLimitTotal(
            CouponActivityLimitTotal limitTotal,
            CouponActivity activity,
            Long userId
    ) {
        if(limitTotal == null){
            insertLimitDay(
                    activity,
                    userId
            );
            return;
        }

        updateLimitDay(limitTotal);
    }

    private void insertLimitDay(
            CouponActivity activity,
            Long userId
    ) {
        CouponActivityLimitTotal insertLimitTotal = new CouponActivityLimitTotal()
                .setUserId(userId)
                .setActivityId(activity.getId());

        limitTotalService.save(insertLimitTotal);
    }

    private void updateLimitDay(CouponActivityLimitTotal limitDay) {
        CouponActivityLimitTotal updateLimitDay = new CouponActivityLimitTotal()
                .setId(limitDay.getId())
                .setVersion(limitDay.getVersion())
                .setNumber(limitDay.getNumber() + 1);

        limitTotalService.updateById(updateLimitDay);
    }

}
