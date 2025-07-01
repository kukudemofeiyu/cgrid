package com.things.cgomp.device.service;

import com.things.cgomp.device.dto.statistics.*;
import com.things.cgomp.order.api.dto.OrderStatisticsReq;

import java.util.List;

/**
 * @author things
 */
public interface ISiteStatisticsService {

    /**
     * 查询统计累计数据
     * @return StatisticsTotalDataResp
     */
    StatisticsTotalDataResp selectTotalData();

    /**
     * 查询站点累计数据
     * @param req 请求对象
     * @return StatisticsSiteDataResp
     */
    StatisticsSiteDataResp selectSiteTotalData(OrderStatisticsReq req);

    /**
     * 查询订单按日期趋势数据
     * @return StatisticsOrderTrendDateResp
     */
    List<StatisticsOrderTrendDateResp> selectOrderDateTrend(OrderStatisticsReq req);

    /**
     * 查询订单按小时趋势数据
     * @return StatisticsOrderTrendHourResp
     */
    List<StatisticsOrderTrendHourResp> selectOrderHourTrend(OrderStatisticsReq req);

    /**
     * 查询充电枪统计
     * @return StatisticsDevicePortData
     */
    StatisticsDevicePortData selectDevicePort(OrderStatisticsReq req);

    /**
     * 查询订单用户趋势数据
     * @return StatisticsOrderTrendUserResp
     */
    List<StatisticsOrderTrendUserResp> selectOrderUserTrend(OrderStatisticsReq req);

    /**
     * 查询APP充值趋势数据
     * @return StatisticsRechargeTrendResp
     */
    List<StatisticsRechargeTrendResp> selectRechargeTrend(OrderStatisticsReq req);

    /**
     * 查询场站经营数据
     * @param req 请求对象
     * @return StatisticsManageDataResp
     */
    StatisticsManageDataResp selectManageData(OrderStatisticsReq req);
}
