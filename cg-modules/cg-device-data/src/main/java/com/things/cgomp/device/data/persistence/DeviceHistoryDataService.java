package com.things.cgomp.device.data.persistence;

import com.google.common.base.CaseFormat;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.datasource.annotation.TDEngine;
import com.things.cgomp.common.device.dao.td.domain.DeviceChargeData;
import com.things.cgomp.common.device.dao.td.domain.DeviceCmdLogData;
import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import com.things.cgomp.common.device.dao.td.domain.PortStatusData;
import com.things.cgomp.common.device.dao.td.mapper.DeviceChargeDataMapper;
import com.things.cgomp.common.device.dao.td.domain.SingleTsValue;
import com.things.cgomp.common.device.dao.td.mapper.DeviceCmdLogMapper;
import com.things.cgomp.common.device.dao.td.mapper.DeviceDataMapper;
import com.things.cgomp.common.device.dao.td.mapper.DynamicsDataMapper;
import com.things.cgomp.common.mybatisplus.query.LambdaQueryWrapperX;
import com.things.cgomp.common.device.pojo.device.HistoryDataQueryReq;
import com.things.cgomp.device.data.constants.HistoryDataRouter;
import com.things.cgomp.order.api.RemoteOrderService;
import com.things.cgomp.order.api.domain.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备历史数据服务
 * @author thigns
 */
@Slf4j
@Service
@TDEngine
public class DeviceHistoryDataService {

    @Resource
    private DeviceDataMapper deviceDataMapper;
    @Resource
    private DeviceCmdLogMapper deviceCmdLogMapper;
    @Resource
    private DynamicsDataMapper dynamicsDataMapper;
    @Resource
    private RemoteOrderService remoteOrderService;
    @Resource
    private DeviceChargeDataMapper deviceChargeDataMapper;

    public List<DevicePortData> selectHistoryData(HistoryDataQueryReq req) {
        R<OrderInfo> orderR = remoteOrderService.getChargeOrderByOrderNo(req.getOrderNo(), SecurityConstants.INNER);
        if (!R.isSuccess(orderR)) {
            return new ArrayList<>();
        }
        OrderInfo order = orderR.getData();
        if (req.getDeviceId() == null) {
            req.setDeviceId(order.getPortId());
        }
        req.setTradeSn(order.getTradeSn());
        return selectHistoryDataByTradeSn(req);
    }

    public List<DevicePortData> selectHistoryDataByTradeSn(HistoryDataQueryReq req) {
        LambdaQueryWrapperX<DevicePortData> wrapper = new LambdaQueryWrapperX<DevicePortData>()
                .eq(DevicePortData::getDeviceId, req.getDeviceId())
                .eq(DevicePortData::getOrderSn, req.getTradeSn())
                .orderByDesc(DevicePortData::getEventTime)
                .last("limit 10000");
        return deviceDataMapper.selectList(wrapper);
    }

    public List<DeviceChargeData> selectChargeHistoryDataByTradeSn(HistoryDataQueryReq req){
        LambdaQueryWrapperX<DeviceChargeData> wrapper = new LambdaQueryWrapperX<DeviceChargeData>()
                .eq(DeviceChargeData::getDeviceId, req.getDeviceId())
                .eq(DeviceChargeData::getOrderSn, req.getTradeSn())
                .orderByDesc(DeviceChargeData::getEventTime)
                .last("limit 10000");
        return deviceChargeDataMapper.selectList(wrapper);
    }

    public List<SingleTsValue> selectHistoryDataByKey(HistoryDataQueryReq req){
        String key = req.getKey();
        if (StringUtils.isEmpty(key)) {
            return new ArrayList<>();
        }
        String table = HistoryDataRouter.getTableByKey(req.getKey());
        if (StringUtils.isEmpty(table)) {
            return new ArrayList<>();
        }
        try {
            req.setTable(table);
            // key转换
            req.setKey(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, req.getKey()));
            return dynamicsDataMapper.selectHistoryDataByKey(req);
        }catch (Exception e){
            log.error("selectHistoryDataByKey error, key=#{}", key, e);
            return new ArrayList<>();
        }
    }

    public int writeDevicePortData(List<DevicePortData> devicePortDataList){
        return deviceDataMapper.insertDevicePortDataBatch(devicePortDataList);
    }

    public int writeDevicePortStatus(List<PortStatusData> portStatusList){
        return deviceDataMapper.insertDevicePortStatusBatch(portStatusList);
    }

    public int writeDeviceCmdLog(List<DeviceCmdLogData> deviceCmdLogData){
        return deviceCmdLogMapper.insertDeviceCmdLogDataBatch(deviceCmdLogData);
    }

    public int writeDeviceChargeDataLog(List<DeviceChargeData> DeviceChargeDatas){
        return deviceChargeDataMapper.insertChargeDataBatch(DeviceChargeDatas);
    }
}
