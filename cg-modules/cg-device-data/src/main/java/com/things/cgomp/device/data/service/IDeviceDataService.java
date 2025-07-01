package com.things.cgomp.device.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import com.things.cgomp.device.data.api.domain.DevicePortAllHistoryData;
import com.things.cgomp.device.data.api.domain.DevicePortAllRealData;
import com.things.cgomp.common.device.pojo.device.DevicePortKeyHistoryData;
import com.things.cgomp.device.data.api.domain.RealDataQueryReq;

import java.util.List;

/**
 * 设备数据服务
 */
public interface IDeviceDataService extends IService<DevicePortData> {

    /**
     * 根据订单号和设备ID查询设备数据
     * @param orderNo 订单号
     * @return List
     */
    List<DevicePortData> selectHistoryData(Long deviceId, String orderNo);

    /**
     * 根据流水号和设备ID查询设备数据
     * @param tradeSn 订单流水号
     * @return List
     */
    List<DevicePortData> selectHistoryDataByTradeSn(Long deviceId, String tradeSn);

    /**
     * 根据流水号和设备ID查询所有设备数据
     * @param tradeSn 订单流水号
     * @return DevicePortAllHistoryData
     */
    DevicePortAllHistoryData selectAllHistoryDataByTradeSn(Long deviceId, String tradeSn);

    /**
     * 根据KEY查询设备历史数据
     * @param deviceId  设备ID
     * @param key       key
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return DevicePortKeyHistoryData
     */
    DevicePortKeyHistoryData selectHistoryDataByKey(Long deviceId, String key, String beginTime, String endTime);

    /**
     * 根据设备ID查询实时数据
     * @param req 请求对象
     * @return DeviceData
     */
    DevicePortData selectRealData(RealDataQueryReq req);

    /**
     * 根据设备ID批量查询实时数据
     * @param reqList 请求对象
     * @return DeviceData
     */
    List<DevicePortData> selectRealDataBatch(List<RealDataQueryReq> reqList);

    /**
     * 根据设备ID查询所有实时数据
     * @param req 请求对象
     * @return DevicePortAllData
     */
    DevicePortAllRealData selectAllRealData(RealDataQueryReq req);
}
