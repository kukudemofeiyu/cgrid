package com.things.cgomp.order.api;

import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.order.api.factory.RemoteOrderStepFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 订单步骤服务
 *
 * @author things
 */
@FeignClient(contextId = "remoteOrderStepService",
        value = ServiceNameConstants.ORDER_SERVICE,
        //url = "http://localhost:9013",
        fallbackFactory = RemoteOrderStepFallbackFactory.class)
public interface RemoteOrderStepService {

    @PostMapping(value = "/orderStep/checkAndProcess", name = "检查并执行订单未完成步骤")
    R<Boolean> checkAndProcessOrderStep(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
