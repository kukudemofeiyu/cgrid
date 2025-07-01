package com.things.cgomp.pay.api.factory;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.pay.api.RemoteCouponService;
import com.things.cgomp.pay.api.domain.Coupon;
import com.things.cgomp.pay.api.vo.DiscountCouponVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
public class RemoteCouponFallbackFactory implements FallbackFactory<RemoteCouponService> {

    @Override
    public RemoteCouponService create(Throwable throwable) {
        log.error("优惠券服务调用失败:{}", throwable.getMessage());
        return new RemoteCouponService() {
            @Override
            public R<Coupon> selectCoupon(Long id) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<List<DiscountCouponVo>> selectSiteActivityAndCoupons(Long orderId) {
                return R.fail(throwable.getMessage());
            }
        };
    }
}
