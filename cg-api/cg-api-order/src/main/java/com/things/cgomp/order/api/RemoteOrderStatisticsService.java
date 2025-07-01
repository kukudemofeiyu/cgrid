package com.things.cgomp.order.api;

import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.order.api.domain.*;
import com.things.cgomp.order.api.dto.OrderStatisticsReq;
import com.things.cgomp.order.api.factory.RemoteOrderStatisticsFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 订单统计服务
 *
 * @author things
 */
@FeignClient(contextId = "remoteOrderStatisticsService",
        value = ServiceNameConstants.ORDER_SERVICE,
//        url = "http://localhost:9013",
        fallbackFactory = RemoteOrderStatisticsFallbackFactory.class)
public interface RemoteOrderStatisticsService {

    @GetMapping(value = "/statistics/getTotal", name = "获取累计数据")
    R<OrderStatisticsData> getOrderStatisticsTotalData(@SpringQueryMap OrderStatisticsReq req,
                                                       @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @GetMapping(value = "/statistics/getDateTrend", name = "获取天趋势数据")
    R<List<OrderTrendDateData>> getOrderDateTrendData(@SpringQueryMap OrderStatisticsReq req,
                                                      @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @GetMapping(value = "/statistics/getHourTrend", name = "获取小时趋势数据")
    R<List<OrderTrendHourData>> getOrderHourTrendData(@SpringQueryMap OrderStatisticsReq req,
                                                      @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @GetMapping(value = "/statistics/getUserTrend", name = "获取用户趋势数据")
    R<List<OrderTrendUserData>> getOrderUserTrendData(@RequestParam(value = "beginDate", required = false) String beginDate,
                                                      @RequestParam(value = "endDate", required = false) String endDate,
                                                      @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @GetMapping(value = "/statistics/getDeviceOrderStatistics", name = "获取设备的订单统计信息")
    R<List<DeviceOrderStatisticsData>> getDeviceOrderStatistics(@RequestParam(value = "deviceIds") List<Long> deviceId);
}
