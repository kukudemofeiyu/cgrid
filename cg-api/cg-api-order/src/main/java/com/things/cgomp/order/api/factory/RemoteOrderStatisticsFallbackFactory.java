package com.things.cgomp.order.api.factory;


import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.order.api.RemoteOrderStatisticsService;
import com.things.cgomp.order.api.domain.*;
import com.things.cgomp.order.api.dto.OrderStatisticsReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 订单统计服务降级处理
 *
 * @author things
 */
@Slf4j
@Component
public class RemoteOrderStatisticsFallbackFactory implements FallbackFactory<RemoteOrderStatisticsService> {

    @Override
    public RemoteOrderStatisticsService create(Throwable throwable) {
        log.error("订单统计服务调用失败:{}", throwable.getMessage());
        return new RemoteOrderStatisticsService() {

            @Override
            public R<OrderStatisticsData> getOrderStatisticsTotalData(OrderStatisticsReq req, String source) {
                return R.fail("查询订单统计累计数据失败:" + throwable.getMessage());
            }

            @Override
            public R<List<OrderTrendDateData>> getOrderDateTrendData(OrderStatisticsReq req, String source) {
                return R.fail("查询订单按天趋势数据查询失败:" + throwable.getMessage());
            }

            @Override
            public R<List<OrderTrendHourData>> getOrderHourTrendData(OrderStatisticsReq req, String source) {
                return R.fail("查询订单按小时趋势数据查询失败:" + throwable.getMessage());
            }

            @Override
            public R<List<OrderTrendUserData>> getOrderUserTrendData(String beginDate, String endDate, String source) {
                return R.fail("查询订单按用户趋势数据查询失败:" + throwable.getMessage());
            }

            @Override
            public R<List<DeviceOrderStatisticsData>> getDeviceOrderStatistics(List<Long> deviceId) {
                return R.fail("获取设备的订单统计信息失败:" + throwable.getMessage());
            }
        };
    }
}
