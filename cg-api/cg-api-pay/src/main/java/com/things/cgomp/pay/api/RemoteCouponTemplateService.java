package com.things.cgomp.pay.api;

import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.pay.api.factory.RemoteCouponTemplateFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(
        contextId = "remoteCouponTemplateService",
        value = ServiceNameConstants.PAY_DEVICE,
        //          url = "http://localhost:9014",
        fallbackFactory = RemoteCouponTemplateFallbackFactory.class
)
public interface RemoteCouponTemplateService {

    @GetMapping(value = "couponTemplate/nameMap", name = "查询优惠券模板名称Map")
    R<Map<Long,String>> getNameMap(
            @RequestParam("ids") List<Long> ids
    );
}
