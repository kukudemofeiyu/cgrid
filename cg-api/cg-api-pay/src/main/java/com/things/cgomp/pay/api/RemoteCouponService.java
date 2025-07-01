package com.things.cgomp.pay.api;

import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.pay.api.domain.Coupon;
import com.things.cgomp.pay.api.factory.RemoteCouponFallbackFactory;
import com.things.cgomp.pay.api.vo.DiscountCouponVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(
        contextId = "remoteCouponService",
        value = ServiceNameConstants.PAY_DEVICE,
        //          url = "http://localhost:9014",
        fallbackFactory = RemoteCouponFallbackFactory.class
)
public interface RemoteCouponService {

    @GetMapping(value = "/coupon/detail", name = "查询优惠券")
    R<Coupon> selectCoupon(
            @RequestParam(value = "id") Long id
    );

    @GetMapping(value = "/coupon/siteActivityAndCoupons", name = "查询站点活动和优惠券")
    R<List<DiscountCouponVo>> selectSiteActivityAndCoupons(
            @RequestParam(value = "orderId") Long orderId
    );
}
