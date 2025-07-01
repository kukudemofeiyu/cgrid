package com.things.cgomp.app.api;

import com.things.cgomp.app.api.domain.AppRechargeTrendData;
import com.things.cgomp.app.api.factory.RemoteAppStatisticsFallbackFactory;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * APP统计服务
 *
 * @author things
 */
@FeignClient(contextId = "remoteAppStatisticsService",
        value = ServiceNameConstants.APP_SERVICE,
        //url = "http://localhost:9016",
        fallbackFactory = RemoteAppStatisticsFallbackFactory.class)
public interface RemoteAppStatisticsService {

    @GetMapping(value = "/statistics/getRechargeTrend", name = "获取充值趋势数据")
    R<List<AppRechargeTrendData>> getRechargeTrendData(@RequestParam(value = "beginDate", required = false) String beginDate,
                                                        @RequestParam(value = "endDate", required = false) String endDate,
                                                        @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
