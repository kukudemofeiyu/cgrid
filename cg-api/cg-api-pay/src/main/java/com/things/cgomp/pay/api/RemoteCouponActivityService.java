package com.things.cgomp.pay.api;

import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.pay.api.factory.RemoteCouponActivityFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(
        contextId = "remoteCouponActivityService",
        value = ServiceNameConstants.PAY_DEVICE,
        //          url = "http://localhost:9014",
        fallbackFactory = RemoteCouponActivityFallbackFactory.class
)
public interface RemoteCouponActivityService {

    @GetMapping(value = "couponActivity/nameMap", name = "查询优惠券活动名称Map")
    R<Map<Long,String>> getNameMap(
            @RequestParam("ids") List<Long> ids
    );

    @PostMapping(value = "couponActivity/grantCoupons", name = "（支付成功后）发放优惠券")
    R<?> grantCoupons(
            @RequestBody OrderInfo orderInfo
    );
}
