package com.things.cgomp.order.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.core.web.domain.TrendQueryDTO;
import com.things.cgomp.common.device.pojo.device.DeviceCount;
import com.things.cgomp.device.api.RemoteDeviceService;
import com.things.cgomp.order.api.domain.*;
import com.things.cgomp.order.api.dto.OrderStatisticsReq;
import com.things.cgomp.order.convert.OrderStatisticsConvert;
import com.things.cgomp.order.domain.ManageDeviceSiteData;
import com.things.cgomp.order.domain.ManageTotalData;
import com.things.cgomp.order.dto.CommissionRecordQueryDTO;
import com.things.cgomp.order.dto.ManageDataReqDTO;
import com.things.cgomp.order.dto.OrderStatisticsQueryDTO;
import com.things.cgomp.order.service.IOrderCommissionRecordService;
import com.things.cgomp.order.service.IOrderInfoService;
import com.things.cgomp.order.service.IOrderStatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.things.cgomp.common.core.utils.PageUtils.startPage;
import static com.things.cgomp.common.core.utils.StatisticsUtils.buildQueryParam;

/**
 * @author things
 */
@Service
public class OrderStatisticsServiceImpl implements IOrderStatisticsService {

    @Resource
    private IOrderInfoService orderInfoService;
    @Resource
    private IOrderCommissionRecordService commissionRecordService;
    @Resource
    private RemoteDeviceService remoteDeviceService;

    @Override
    public ManageTotalData selectManageTotalData(ManageDataReqDTO reqDTO) {
        // 获取订单统计总数据
        OrderStatisticsQueryDTO queryDTO = OrderStatisticsConvert.INSTANCE.convertQueryDTO(reqDTO);
        OrderStatisticsData statisticsTotalData = orderInfoService.selectStatisticsTotalData(queryDTO);
        // 获取设备数量
        return fillDeviceCount(reqDTO, statisticsTotalData);
    }

    @Override
    public PageInfo<ManageDeviceSiteData> selectManageDeviceSiteData(ManageDataReqDTO reqDTO) {
        OrderStatisticsQueryDTO queryDTO = OrderStatisticsConvert.INSTANCE.convertQueryDTO(reqDTO);
        startPage();
        List<OrderStatisticsData> list = orderInfoService.selectStatisticsDeviceData(queryDTO);
        return new PageInfo<>(OrderStatisticsConvert.INSTANCE.convertMangeDeviceRespList(list));
    }

    @Override
    public OrderStatisticsData selectTotalData(OrderStatisticsReq req) {
        // 查询订单统计统计
        OrderStatisticsQueryDTO queryDTO = OrderStatisticsConvert.INSTANCE.convertQueryDTO(req);
        OrderStatisticsData statisticsData = orderInfoService.selectStatisticsTotalData(queryDTO);
        // 查询订单分成数据
        CommissionRecordQueryDTO commissionQueryDTO = OrderStatisticsConvert.INSTANCE.convertCommissionQueryDTO(req);
        CommissionRecordData commissionData = commissionRecordService.getTotalData(commissionQueryDTO);
        statisticsData.setCommissionAmount(commissionData.getOperatorAmount());
        return statisticsData;
    }

    @Override
    public List<OrderTrendDateData> selectDateTrendData(OrderStatisticsReq req) {
        TrendQueryDTO queryDTO = OrderStatisticsConvert.INSTANCE.convertTrendQuery(req);
        buildQueryParam(queryDTO, req.getBeginTime(), req.getEndTime());
        return orderInfoService.selectDateTrendData(queryDTO);
    }

    @Override
    public List<OrderTrendHourData> selectHourTrendData(OrderStatisticsReq req) {
        TrendQueryDTO queryDTO = OrderStatisticsConvert.INSTANCE.convertTrendQuery(req);
        String beginDate = StringUtils.isEmpty(req.getBeginTime()) ? DateUtil.today() : req.getBeginTime();
        queryDTO.setBeginDate(beginDate);
        return orderInfoService.selectHourTrendData(queryDTO);
    }

    @Override
    public List<OrderTrendUserData> selectUserTrendData(String beginDate, String endDate) {
        TrendQueryDTO queryDTO = buildQueryParam(beginDate, endDate);
        return orderInfoService.selectUserTrendData(queryDTO);
    }

    @Override
    public List<DeviceOrderStatisticsData> getDeviceOrderStatistics(List<Long> deviceId) {
        OrderStatisticsQueryDTO queryDTO = new OrderStatisticsQueryDTO();
        queryDTO.setPileIds(deviceId);
        return orderInfoService.selectDeviceOrderStatistics(queryDTO);
    }

    private ManageTotalData fillDeviceCount(ManageDataReqDTO req, OrderStatisticsData statisticsData) {
        ManageTotalData resp = OrderStatisticsConvert.INSTANCE.convertManageTotalResp(statisticsData);
        R<DeviceCount> deviceCountR = remoteDeviceService.getPileDeviceCount(
                req.getOperatorId(), req.getSiteId(), req.getPileSn(), SecurityConstants.INNER);
        if (R.SUCCESS != deviceCountR.getCode() || deviceCountR.getData() == null) {
            resp.setDeviceEmpty();
        } else {
            DeviceCount countData = deviceCountR.getData();
            resp.setDeviceCount(countData.getTotalCount());
            resp.setOnlineCount(countData.getOnlineCount());
            resp.setOfflineCount(countData.getOfflineCount());
        }
        return resp;
    }
}
