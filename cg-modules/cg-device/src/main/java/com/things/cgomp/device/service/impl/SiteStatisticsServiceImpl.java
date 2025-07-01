package com.things.cgomp.device.service.impl;

import cn.hutool.core.date.DateUtil;
import com.things.cgomp.app.api.RemoteAppStatisticsService;
import com.things.cgomp.app.api.domain.AppRechargeTrendData;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.device.enums.ComponentEnum;
import com.things.cgomp.common.device.pojo.device.DeviceCount;
import com.things.cgomp.common.device.pojo.device.DeviceCountReqDTO;
import com.things.cgomp.device.convert.StatisticsConvert;
import com.things.cgomp.device.dto.SiteStatisticsQueryDTO;
import com.things.cgomp.device.dto.statistics.*;
import com.things.cgomp.device.dto.statistics.total.trend.TotalLongTrend;
import com.things.cgomp.device.dto.statistics.total.trend.TotalTrendData;
import com.things.cgomp.device.mapper.StatisticsMapper;
import com.things.cgomp.device.service.IDeviceInfoService;
import com.things.cgomp.device.service.ISiteStatisticsService;
import com.things.cgomp.order.api.RemoteOrderStatisticsService;
import com.things.cgomp.order.api.domain.OrderStatisticsData;
import com.things.cgomp.order.api.domain.OrderTrendDateData;
import com.things.cgomp.order.api.domain.OrderTrendHourData;
import com.things.cgomp.order.api.domain.OrderTrendUserData;
import com.things.cgomp.order.api.dto.OrderStatisticsReq;
import com.things.cgomp.system.api.RemoteAppUserService;
import com.things.cgomp.system.api.domain.AppUserTrendDateData;
import com.things.cgomp.system.api.dto.AppUserTrendDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author things
 */
@Slf4j
@Service
public class SiteStatisticsServiceImpl implements ISiteStatisticsService {

    @Resource
    private StatisticsMapper statisticsMapper;
    @Resource
    private RemoteOrderStatisticsService remoteOrderStatisticsService;
    @Resource
    private RemoteAppUserService remoteAppUserService;
    @Resource
    private RemoteAppStatisticsService remoteAppStatisticsService;
    @Resource
    private IDeviceInfoService deviceInfoService;

    @Override
    public StatisticsTotalDataResp selectTotalData() {
        // 获取订单统计数据
        R<OrderStatisticsData> totalR = remoteOrderStatisticsService.getOrderStatisticsTotalData(new OrderStatisticsReq(), SecurityConstants.INNER);
        if (!R.isSuccess(totalR)) {
            log.error("selectTotalData 获取订单统计数据失败, msg={}", totalR.getMsg());
        }
        StatisticsTotalDataResp resp = StatisticsConvert.INSTANCE.convertTotal(totalR.getData());
        // 获取订单趋势数据
        R<List<OrderTrendDateData>> trendDataR = remoteOrderStatisticsService.getOrderDateTrendData(new OrderStatisticsReq(), SecurityConstants.INNER);
        if (R.isSuccess(trendDataR)) {
            List<OrderTrendDateData> trendData = trendDataR.getData();
            buildOrderTrendData(resp, trendData);
        }
        // 获取注册用户趋势数据
        R<AppUserTrendDataDTO> appUserTrendR = remoteAppUserService.getTotalTrend(SecurityConstants.INNER);
        if (R.isSuccess(appUserTrendR) && appUserTrendR.getData() != null) {
            buildUserTrendData(resp, appUserTrendR.getData());
        }
        return resp;
    }

    @Override
    public StatisticsSiteDataResp selectSiteTotalData(OrderStatisticsReq req) {
        // 获取统计数据
        R<OrderStatisticsData> totalR = remoteOrderStatisticsService.getOrderStatisticsTotalData(req, SecurityConstants.INNER);
        if (!R.isSuccess(totalR)) {
            log.error("selectTotalData 获取订单统计数据失败, msg={}", totalR.getMsg());
        }
        return StatisticsConvert.INSTANCE.convertSiteTotal(totalR.getData());
    }

    @Override
    public List<StatisticsOrderTrendDateResp> selectOrderDateTrend(OrderStatisticsReq req) {
        // 获取订单趋势数据
        if (StringUtils.hasLength(req.getBeginTime()) && StringUtils.hasLength(req.getEndTime())
            && req.getBeginTime().equals(req.getEndTime())) {
            // 当天统计按照小时趋势查询
            R<List<OrderTrendHourData>> trendDataR = remoteOrderStatisticsService.getOrderHourTrendData(req, SecurityConstants.INNER);
            if (R.isSuccess(trendDataR)) {
                List<OrderTrendHourData> trendData = trendDataR.getData();
                return StatisticsConvert.INSTANCE.convertHourTrendList2(trendData);
            }
        } else {
            R<List<OrderTrendDateData>> trendDataR = remoteOrderStatisticsService.getOrderDateTrendData(req, SecurityConstants.INNER);
            if (R.isSuccess(trendDataR)) {
                List<OrderTrendDateData> trendData = trendDataR.getData();
                return StatisticsConvert.INSTANCE.convertDateTrendList(trendData);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<StatisticsOrderTrendHourResp> selectOrderHourTrend(OrderStatisticsReq req) {
        // 获取订单分时趋势数据
        R<List<OrderTrendHourData>> trendDataR = remoteOrderStatisticsService.getOrderHourTrendData(req, SecurityConstants.INNER);
        if (R.isSuccess(trendDataR)) {
            List<OrderTrendHourData> trendData = trendDataR.getData();
            return StatisticsConvert.INSTANCE.convertHourTrendList(trendData);
        }
        return Collections.emptyList();
    }

    @Override
    @DataScope(orgAlias = "os", userSiteAlias = "us", userOperatorAlias = "uop")
    public StatisticsDevicePortData selectDevicePort(OrderStatisticsReq req) {
        SiteStatisticsQueryDTO queryDTO = new SiteStatisticsQueryDTO().setSiteId(req.getSiteId());
        return statisticsMapper.selectDevicePortCount(queryDTO);
    }

    private void buildOrderTrendData(StatisticsTotalDataResp resp, List<OrderTrendDateData> trendData) {
        if (CollectionUtils.isEmpty(trendData)) {
            return;
        }
        List<TotalTrendData<BigDecimal>> amountTrends = new ArrayList<>(trendData.size());
        List<TotalTrendData<Long>> countTrends = new ArrayList<>(trendData.size());
        List<TotalTrendData<BigDecimal>> electricityTrends = new ArrayList<>(trendData.size());
        List<TotalTrendData<BigDecimal>> timeTrends = new ArrayList<>(trendData.size());
        trendData.forEach(data -> {
            String date = data.getDate();
            amountTrends.add(new TotalTrendData<>(date, data.getOrderAmount()));
            countTrends.add(new TotalTrendData<>(date, data.getChargeCount()));
            electricityTrends.add(new TotalTrendData<>(date, data.getConsumeElectricity()));
            timeTrends.add(new TotalTrendData<>(date, data.getChargeTime()));
            if (DateUtil.today().equals(date)) {
                // 填充今日数据
                resp.fillTodayValue(data.getOrderAmount(), data.getChargeCount(), data.getConsumeElectricity(), data.getChargeTime());
            }
        });
        resp.fillTrends(amountTrends, countTrends, electricityTrends, timeTrends);
    }

    @Override
    public List<StatisticsOrderTrendUserResp> selectOrderUserTrend(OrderStatisticsReq req) {
        // 获取订单用户趋势数据
        R<List<OrderTrendUserData>> trendDataR = remoteOrderStatisticsService.getOrderUserTrendData(req.getBeginTime(), req.getEndTime(), SecurityConstants.INNER);
        if (R.isSuccess(trendDataR)) {
            List<OrderTrendUserData> trendData = trendDataR.getData();
            return StatisticsConvert.INSTANCE.convertUserTrendList(trendData);
        }
        return Collections.emptyList();
    }

    @Override
    public List<StatisticsRechargeTrendResp> selectRechargeTrend(OrderStatisticsReq req) {
        // 获取APP充值趋势数据
        R<List<AppRechargeTrendData>> trendDataR = remoteAppStatisticsService.getRechargeTrendData(req.getBeginTime(), req.getEndTime(), SecurityConstants.INNER);
        if (R.isSuccess(trendDataR)) {
            List<AppRechargeTrendData> trendData = trendDataR.getData();
            return StatisticsConvert.INSTANCE.convertRechargeTrendList(trendData);
        }
        return Collections.emptyList();
    }

    @Override
    public StatisticsManageDataResp selectManageData(OrderStatisticsReq req) {
        // 获取订单统计数据
        R<OrderStatisticsData> totalR = remoteOrderStatisticsService.getOrderStatisticsTotalData(req, SecurityConstants.INNER);
        if (!R.isSuccess(totalR)) {
            log.error("selectTotalData 获取订单统计数据失败, msg={}", totalR.getMsg());
            return null;
        }
        StatisticsManageDataResp resp = StatisticsConvert.INSTANCE.convertManage(totalR.getData());
        // 获取设备数量
        DeviceCountReqDTO reqDTO = DeviceCountReqDTO.builder()
                .siteId(req.getSiteId())
                .deviceId(req.getPileId())
                .component(ComponentEnum.PILE.getType())
                .build();
        DeviceCount deviceCount = deviceInfoService.selectDeviceCount(reqDTO);
        resp.setDeviceCount(deviceCount.getTotalCount());
        resp.setOnlineCount(deviceCount.getOnlineCount());
        resp.setOfflineCount(deviceCount.getOfflineCount());
        return resp;
    }

    private void buildUserTrendData(StatisticsTotalDataResp resp, AppUserTrendDataDTO trendData) {
        TotalLongTrend userTrend = new TotalLongTrend(trendData.getTotalCount());
        List<AppUserTrendDateData> trends = trendData.getDateTrends();
        if (!CollectionUtils.isEmpty(trends)) {
            List<TotalTrendData<Long>> countTrends = new ArrayList<>(trends.size());
            trends.forEach(data -> {
                String date = data.getDate();
                countTrends.add(new TotalTrendData<>(date, data.getUserCount()));
                if (DateUtil.today().equals(date)) {
                    // 填充今日用户
                    userTrend.setValue(data.getUserCount());
                }
            });
            userTrend.setTrends(countTrends);
        }
        resp.setUserCount(userTrend);
    }
}
