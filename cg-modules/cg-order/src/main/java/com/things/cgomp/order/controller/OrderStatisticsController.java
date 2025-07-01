package com.things.cgomp.order.controller;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.security.annotation.InnerAuth;
import com.things.cgomp.order.api.domain.*;
import com.things.cgomp.order.api.dto.OrderStatisticsReq;
import com.things.cgomp.order.service.IOrderStatisticsService;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单统计
 *
 * @author things
 */
@Log(title = "订单统计")
@RestController
@RequestMapping("/statistics")
public class OrderStatisticsController {

    @Resource
    private IOrderStatisticsService orderStatisticsService;

    @InnerAuth
    @GetMapping("/getTotal")
    public R<OrderStatisticsData> getTotal(@SpringQueryMap OrderStatisticsReq req) {
        return R.ok(orderStatisticsService.selectTotalData(req));
    }

    @InnerAuth
    @GetMapping("/getDateTrend")
    public R<List<OrderTrendDateData>> getDateTrend(@SpringQueryMap OrderStatisticsReq req) {
        return R.ok(orderStatisticsService.selectDateTrendData(req));
    }

    @InnerAuth
    @GetMapping("/getHourTrend")
    public R<List<OrderTrendHourData>> getHourTrend(@SpringQueryMap OrderStatisticsReq req) {
        return R.ok(orderStatisticsService.selectHourTrendData(req));
    }

    @InnerAuth
    @GetMapping("/getUserTrend")
    public R<List<OrderTrendUserData>> getUserTrend(@RequestParam(value = "beginDate", required = false) String beginDate,
                                                    @RequestParam(value = "endDate", required = false) String endDate) {
        return R.ok(orderStatisticsService.selectUserTrendData(beginDate, endDate));
    }

    @GetMapping("/getDeviceOrderStatistics")
    public R<List<DeviceOrderStatisticsData>> getDeviceOrderStatistics(@RequestParam(value = "deviceIds") List<Long> deviceIds){
        return R.ok(orderStatisticsService.getDeviceOrderStatistics(deviceIds));
    }
}
