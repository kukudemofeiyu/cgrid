package com.things.cgomp.app.api.factory;


import com.things.cgomp.app.api.RemoteAppStatisticsService;
import com.things.cgomp.app.api.domain.AppRechargeTrendData;
import com.things.cgomp.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * APP统计服务降级处理
 *
 * @author things
 */
@Slf4j
@Component
public class RemoteAppStatisticsFallbackFactory implements FallbackFactory<RemoteAppStatisticsService> {

    @Override
    public RemoteAppStatisticsService create(Throwable throwable) {
        log.error("APP统计服务调用失败:{}", throwable.getMessage());
        return new RemoteAppStatisticsService() {

            @Override
            public R<List<AppRechargeTrendData>> getRechargeTrendData(String beginDate, String endDate, String source) {
                return R.fail("查询充值趋势数据查询失败:" + throwable.getMessage());
            }
        };
    }
}
