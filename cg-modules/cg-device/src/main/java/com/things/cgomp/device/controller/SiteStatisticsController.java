package com.things.cgomp.device.controller;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.device.dto.statistics.*;
import com.things.cgomp.device.service.ISiteStatisticsService;
import com.things.cgomp.order.api.dto.OrderStatisticsReq;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author things
 */
@Log(title = "站点数据统计")
@RestController
@RequestMapping("/statistics")
public class SiteStatisticsController {

    @Resource
    private ISiteStatisticsService statisticsService;

    @RequiresPermissions("device:site:statistics:total")
    @GetMapping(value = "/getTotal", name = "查询累计数据")
    public R<StatisticsTotalDataResp> getTotal(){
        return R.ok(statisticsService.selectTotalData());
    }

    @RequiresPermissions("device:site:statistics:station")
    @GetMapping(value = "/getSiteStatistics", name = "查询场站数据")
    public R<StatisticsSiteDataResp> getSiteStatistics(OrderStatisticsReq req){
        return R.ok(statisticsService.selectSiteTotalData(req));
    }

    @RequiresPermissions("device:site:statistics:date")
    @GetMapping(value = "/getDateStatistics", name = "查询订单天统计")
    public R<List<StatisticsOrderTrendDateResp>> getDateStatistics(OrderStatisticsReq req){
        return R.ok(statisticsService.selectOrderDateTrend(req));
    }

    @RequiresPermissions("device:site:statistics:hour")
    @GetMapping(value = "/getHourStatistics", name = "查询订单分时统计")
    public R<List<StatisticsOrderTrendHourResp>> getHourStatistics(OrderStatisticsReq req){
        return R.ok(statisticsService.selectOrderHourTrend(req));
    }

    @RequiresPermissions("device:site:statistics:realStatus")
    @GetMapping(value = "getRealStatus", name = "查询设备实时状态")
    public R<StatisticsDevicePortData> getRealStatus(OrderStatisticsReq req){
        return R.ok(statisticsService.selectDevicePort(req));
    }

    @RequiresPermissions("device:site:statistics:user")
    @GetMapping(value = "/getUserStatistics", name = "查询订单用户统计")
    public R<List<StatisticsOrderTrendUserResp>> getUserStatistics(OrderStatisticsReq req){
        return R.ok(statisticsService.selectOrderUserTrend(req));
    }

    @RequiresPermissions("device:site:statistics:recharge")
    @GetMapping(value = "/getRechargeStatistics", name = "查询充值统计")
    public R<List<StatisticsRechargeTrendResp>> getRechargeStatistics(OrderStatisticsReq req){
        return R.ok(statisticsService.selectRechargeTrend(req));
    }

    @RequiresPermissions("device:site:statistics:manage")
    @GetMapping(value = "/getManageData", name = "查询站点经营数据")
    public R<StatisticsManageDataResp> getManageData(OrderStatisticsReq req){
        return R.ok(statisticsService.selectManageData(req));
    }
}
