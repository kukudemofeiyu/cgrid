package com.things.cgomp.order.service;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.order.api.domain.*;
import com.things.cgomp.order.api.dto.OrderStatisticsReq;
import com.things.cgomp.order.domain.ManageDeviceSiteData;
import com.things.cgomp.order.domain.ManageTotalData;
import com.things.cgomp.order.dto.ManageDataReqDTO;

import java.util.List;

/**
 * @author things
 */
public interface IOrderStatisticsService {

    /**
     * 获取订单总数据
     * @param reqDTO   请求参数
     * @return ManageTotalData
     */
    ManageTotalData selectManageTotalData(ManageDataReqDTO reqDTO);

    /**
     * 基于设备、站点维度查询订单数据
     * @param reqDTO 请求参数
     * @return ManageDeviceSiteData
     */
    PageInfo<ManageDeviceSiteData> selectManageDeviceSiteData(ManageDataReqDTO reqDTO);

    /**
     * 查询订单累计数据
     * @param req 请求参数
     * @return OrderStatisticsData
     */
    OrderStatisticsData selectTotalData(OrderStatisticsReq req);

    /**
     * 获取天维度趋势数据
     * @param req 请求参数
     * @return OrderTrendDateData
     */
    List<OrderTrendDateData> selectDateTrendData(OrderStatisticsReq req);

    /**
     * 获取小时维度趋势数据
     * @param req 请求参数
     * @return OrderTrendHourData
     */
    List<OrderTrendHourData> selectHourTrendData(OrderStatisticsReq req);

    /**
     * 获取订单用户趋势数据
     * @param beginDate   开始日期
     * @param endDate     结束日期
     * @return OrderTrendUserData
     */
    List<OrderTrendUserData> selectUserTrendData(String beginDate, String endDate);

    /**
     * 获取设备的订单统计信息
     * @param deviceId
     * @return
     */
    List<DeviceOrderStatisticsData> getDeviceOrderStatistics(List<Long> deviceId);
}
