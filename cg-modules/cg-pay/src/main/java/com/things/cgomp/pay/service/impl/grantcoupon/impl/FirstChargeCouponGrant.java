package com.things.cgomp.pay.service.impl.grantcoupon.impl;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.enums.EnableEnum;
import com.things.cgomp.pay.domain.CouponActivity;
import com.things.cgomp.pay.service.impl.grantcoupon.CouponGrant;
import com.things.cgomp.system.api.RemoteAppUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class FirstChargeCouponGrant extends CouponGrant {

    @Resource
    private RemoteAppUserService remoteAppUserService;

    @Override
    public void grant(
            CouponActivity activity,
            Long userId
    ) {
        Integer firstChargeStatus = getFirstChargeStatus(userId);
        if (!EnableEnum.ENABLE.getCode().equals(firstChargeStatus)) {
            return;
        }

        activity.buildCoupons()
                .forEach(activityCoupon -> grant(
                        activity,
                        userId,
                        activityCoupon
                ));
    }

    private Integer getFirstChargeStatus(Long userId) {
        R<Integer> firstChargeStatusR = remoteAppUserService.selectFirstChargeStatus(userId);
        return firstChargeStatusR.getData();
    }
}
