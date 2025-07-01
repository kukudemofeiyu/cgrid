package com.things.cgomp.device.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.common.device.dao.td.domain.BasePersistData;
import com.things.cgomp.common.device.dao.td.domain.DeviceChargeData;
import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import com.things.cgomp.common.device.dao.td.domain.SingleTsValue;
import com.things.cgomp.common.device.dao.td.mapper.DeviceDataMapper;
import com.things.cgomp.common.device.pojo.device.DevicePortKeyHistoryData;
import com.things.cgomp.common.device.pojo.device.HistoryDataQueryReq;
import com.things.cgomp.device.data.api.domain.DevicePortAllHistoryData;
import com.things.cgomp.device.data.api.domain.DevicePortAllRealData;
import com.things.cgomp.device.data.api.domain.RealDataQueryReq;
import com.things.cgomp.device.data.constants.RealDataType;
import com.things.cgomp.device.data.convert.DeviceChargeDataConvert;
import com.things.cgomp.device.data.persistence.DeviceHistoryDataService;
import com.things.cgomp.device.data.persistence.DeviceRealDataService;
import com.things.cgomp.device.data.service.IDeviceDataService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 订单数据服务实现类
 */
@Component
public class DeviceDataServiceImpl extends ServiceImpl<DeviceDataMapper, DevicePortData> implements IDeviceDataService {

    @Resource
    private DeviceRealDataService deviceRealDataService;
    @Resource
    private DeviceHistoryDataService deviceHistoryDataService;

    @Override
    public List<DevicePortData> selectHistoryData(Long deviceId, String orderNo) {
        HistoryDataQueryReq req = HistoryDataQueryReq.builder().deviceId(deviceId).orderNo(orderNo).build();
        return deviceHistoryDataService.selectHistoryData(req);
    }

    @Override
    public List<DevicePortData> selectHistoryDataByTradeSn(Long deviceId, String tradeSn) {
        HistoryDataQueryReq req = HistoryDataQueryReq.builder().deviceId(deviceId).tradeSn(tradeSn).build();
        return deviceHistoryDataService.selectHistoryDataByTradeSn(req);
    }

    @Override
    public DevicePortAllHistoryData selectAllHistoryDataByTradeSn(Long deviceId, String tradeSn) {
        DevicePortAllHistoryData historyData = new DevicePortAllHistoryData();
        // 查询监测数据
        HistoryDataQueryReq req = HistoryDataQueryReq.builder().deviceId(deviceId).tradeSn(tradeSn).build();
        List<DevicePortData> portDataList = deviceHistoryDataService.selectHistoryDataByTradeSn(req);
        if (!CollectionUtils.isEmpty(portDataList)) {
            historyData.setMonitorData(portDataList);
        }
        // 查询BMS电能数据
        List<DeviceChargeData> chargeDataList = deviceHistoryDataService.selectChargeHistoryDataByTradeSn(req);
        if (!CollectionUtils.isEmpty(chargeDataList)) {
            historyData.setBmsDemandData(DeviceChargeDataConvert.INSTANCE.convertDemandRespList(chargeDataList));
        }
        return historyData;
    }

    @Override
    public DevicePortKeyHistoryData selectHistoryDataByKey(Long deviceId, String key, String beginTime, String endTime) {
        HistoryDataQueryReq req = HistoryDataQueryReq.builder().deviceId(deviceId).key(key).beginTime(beginTime).endTime(endTime).build();
        List<SingleTsValue> resultList = deviceHistoryDataService.selectHistoryDataByKey(req);
        return new DevicePortKeyHistoryData(
                deviceId,
                key,
                resultList == null ? new ArrayList<>() : resultList
        );
    }

    @Override
    public DevicePortData selectRealData(RealDataQueryReq req) {
        return deviceRealDataService.selectByDeviceIdAndOrderNo(req, RealDataType.MONITOR);
    }

    @Override
    public List<DevicePortData> selectRealDataBatch(List<RealDataQueryReq> reqList) {
        return deviceRealDataService.selectByDeviceIdsAndOrderNos(reqList, RealDataType.MONITOR);
    }

    @Override
    public DevicePortAllRealData selectAllRealData(RealDataQueryReq req) {
        DevicePortAllRealData allData = new DevicePortAllRealData();
        Map<String, ? extends BasePersistData> dataMap = deviceRealDataService.selectByDeviceIdAndOrderNo(req);
        if (CollectionUtils.isEmpty(dataMap)) {
            return allData;
        }
        dataMap.forEach((k, v) -> fillData(allData, k, v));
        return allData;
    }

    private void fillData(DevicePortAllRealData allData, String tag, BasePersistData tagData) {
        RealDataType dataType = RealDataType.getByTag(tag);
        if (dataType == null) {
            return;
        }
        switch (dataType) {
            case MONITOR:
                allData.buildMonitor(tagData);
                break;
            case BMS_DEMAND:
                allData.buildBmsDemand(tagData);
                break;
            case BMS_INFO:
                break;
        }
    }
}
