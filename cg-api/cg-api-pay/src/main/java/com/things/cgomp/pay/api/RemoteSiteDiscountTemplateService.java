package com.things.cgomp.pay.api;

import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.pay.api.factory.RemoteSiteDiscountTemplateFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(
        contextId = "remoteSiteDiscountTemplateService",
        value = ServiceNameConstants.PAY_DEVICE,
        //          url = "http://localhost:9014",
        fallbackFactory = RemoteSiteDiscountTemplateFallbackFactory.class
)
public interface RemoteSiteDiscountTemplateService {

    @GetMapping(value = "siteDiscountTemplate/nameMap", name = "查询站点活动模板名称Map")
    R<Map<Long,String>> getTemplateNameMap(
            @RequestParam("ids") List<Long> ids
    );
}
