package com.things.cgomp.order.api.factory;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.order.api.RemoteOrderStepService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 订单步骤服务降级处理
 *
 * @author things
 */
@Slf4j
@Component
public class RemoteOrderStepFallbackFactory implements FallbackFactory<RemoteOrderStepService> {

    @Override
    public RemoteOrderStepService create(Throwable throwable) {
        log.error("订单步骤服务调用失败:{}", throwable.getMessage());
        return new RemoteOrderStepService() {

            @Override
            public R<Boolean> checkAndProcessOrderStep(String source) {
                return R.fail("检查订单未完成步骤失败:" + throwable.getMessage());
            }
        };
    }
}
