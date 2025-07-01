package com.things.cgomp.pay.api.factory;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.pay.api.RemoteCouponActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 计费策略服务降级处理
 *
 * @author things
 */
@Slf4j
@Component
public class RemoteCouponActivityFallbackFactory implements FallbackFactory<RemoteCouponActivityService> {

    @Override
    public RemoteCouponActivityService create(Throwable throwable) {
        log.error("优惠券活动服务调用失败:{}", throwable.getMessage());
        return new RemoteCouponActivityService() {

            @Override
            public R<Map<Long, String>> getNameMap(List<Long> ids) {
                return R.fail(
                        new HashMap<>(),
                        throwable.getMessage()
                );
            }

            @Override
            public R<?> grantCoupons(OrderInfo orderInfo) {
                return R.fail(
                        throwable.getMessage()
                );
            }
        };
    }
}
