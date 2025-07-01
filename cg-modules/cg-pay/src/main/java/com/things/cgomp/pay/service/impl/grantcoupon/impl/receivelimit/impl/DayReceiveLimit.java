package com.things.cgomp.pay.service.impl.grantcoupon.impl.receivelimit.impl;

import com.things.cgomp.pay.domain.CouponActivity;
import com.things.cgomp.pay.domain.CouponActivityLimitDay;
import com.things.cgomp.pay.service.ICouponActivityLimitDayService;
import com.things.cgomp.pay.service.impl.grantcoupon.impl.receivelimit.ReceiveLimit;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;

@Component
public class DayReceiveLimit extends ReceiveLimit {

    @Resource
    private ICouponActivityLimitDayService limitDayService;

    @Override
    public void grantCoupons(
            CouponActivity activity,
            Long userId
    ) {
        Integer receiveLimit = activity.buildReceiveLimit();
        if(receiveLimit == null){
            return;
        }

        LocalDate date = LocalDate.now();
        CouponActivityLimitDay limitDay = limitDayService.selectLimit(
                userId,
                activity.getId(),
                date
        );

        if(limitDay != null && limitDay.getNumber() >= receiveLimit){
            return;
        }

        activity.buildCoupons()
                .forEach(activityCoupon -> grantCoupons(
                        activity,
                        userId,
                        activityCoupon
                ));

        saveLimitDay(
                limitDay,
                activity,
                userId,
                date
        );

    }

    private void saveLimitDay(
            CouponActivityLimitDay limitDay,
            CouponActivity activity,
            Long userId,
            LocalDate date
    ) {
        if(limitDay == null){
            insertLimitDay(
                    activity,
                    userId,
                    date
            );
            return;
        }

        updateLimitDay(limitDay);
    }

    private void insertLimitDay(
            CouponActivity activity,
            Long userId,
            LocalDate date
    ) {
        CouponActivityLimitDay insertLimitDay = new CouponActivityLimitDay()
                .setUserId(userId)
                .setActivityId(activity.getId())
                .setDate(date);

        limitDayService.save(insertLimitDay);
    }

    private void updateLimitDay(CouponActivityLimitDay limitDay) {
        CouponActivityLimitDay updateLimitDay = new CouponActivityLimitDay()
                .setId(limitDay.getId())
                .setVersion(limitDay.getVersion())
                .setNumber(limitDay.getNumber() + 1);

        limitDayService.updateById(updateLimitDay);
    }

}
