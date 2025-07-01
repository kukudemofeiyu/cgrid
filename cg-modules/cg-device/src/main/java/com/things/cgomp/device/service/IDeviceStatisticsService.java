package com.things.cgomp.device.service;

import com.things.cgomp.device.dto.statistics.ChargeGridStatisticsTotalDataResp;
import com.things.cgomp.order.api.dto.OrderStatisticsReq;

/**
 * @author things
 */
public interface IDeviceStatisticsService {

    /**
     * 查询充电桩经营数据
     * @param req 请求对象
     * @return ChargeGridStatisticsTotalDataResp
     */
    ChargeGridStatisticsTotalDataResp selectGridManageData(OrderStatisticsReq req);
}
