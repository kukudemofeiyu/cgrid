package com.things.cgomp.pay.api;

import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.pay.api.domain.SiteDiscountActivity;
import com.things.cgomp.pay.api.factory.RemoteSiteDiscountActivityFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(
        contextId = "remoteSiteDiscountActivityService",
        value = ServiceNameConstants.PAY_DEVICE,
        //          url = "http://localhost:9014",
        fallbackFactory = RemoteSiteDiscountActivityFallbackFactory.class
)
public interface RemoteSiteDiscountActivityService {

    @GetMapping(value = "siteDiscountActivity", name = "查询站点折扣活动")
    R<SiteDiscountActivity> getActivity(
            @RequestParam("id") Long id
    );

    @GetMapping(value = "siteDiscountActivity/activityNameMap", name = "查询站点活动名称Map")
    R<Map<Long,String>> getActivityNameMap(
            @RequestParam("ids") List<Long> ids
    );
}
