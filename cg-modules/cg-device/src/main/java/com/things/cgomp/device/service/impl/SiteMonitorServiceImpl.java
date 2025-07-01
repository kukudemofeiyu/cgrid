package com.things.cgomp.device.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.device.dao.device.mapper.DeviceInfoMapper;
import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import com.things.cgomp.common.device.enums.PortStatusEnum;
import com.things.cgomp.common.device.pojo.device.DevicePortDTO;
import com.things.cgomp.common.device.pojo.device.DevicePortVo;
import com.things.cgomp.device.convert.SiteMonitorConvert;
import com.things.cgomp.device.data.api.RemoteDeviceDataService;
import com.things.cgomp.device.data.api.domain.RealDataQueryReq;
import com.things.cgomp.device.dto.monitor.SiteMonitorDeviceReq;
import com.things.cgomp.device.dto.monitor.SiteMonitorDeviceResp;
import com.things.cgomp.device.service.ISiteMonitorService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.things.cgomp.common.core.utils.PageUtils.startPage;
import static com.things.cgomp.common.core.utils.SpringUtils.getAopProxy;

/**
 * @author things
 */
@Service
public class SiteMonitorServiceImpl implements ISiteMonitorService {

    @Resource
    private DeviceInfoMapper deviceInfoMapper;
    @Resource
    private RemoteDeviceDataService remoteDeviceDataService;

    @Override
    public PageInfo<SiteMonitorDeviceResp> selectDevicePage(SiteMonitorDeviceReq req) {
        // 分页查询枪口列表
        startPage();
        List<DevicePortVo> deviceList = getAopProxy(this).selectDevicePortList(req);
        if (CollectionUtils.isEmpty(deviceList)) {
            return new PageInfo<>(new ArrayList<>());
        }
        // 获取正在充电的设备
        Map<Long, String> chargingDevice = deviceList.stream()
                .filter(d -> PortStatusEnum.CHARGE.getType().equals(d.getPortStatus()))
                .collect(Collectors.toMap(DevicePortVo::getDeviceId, DevicePortVo::getOrderSn));
        Map<Long, DevicePortData> deviceRealData = new HashMap<>();
        if (!CollectionUtils.isEmpty(chargingDevice)) {
            // 查询最新的设备数据
            List<RealDataQueryReq> reqList = new ArrayList<>(chargingDevice.size());
            chargingDevice.forEach((k, v) -> reqList.add(RealDataQueryReq.builder().deviceId(k).orderSn(v).build()));
            R<List<DevicePortData>> dataR = remoteDeviceDataService.selectRealDataByOrderNo(JSON.toJSONString(reqList), SecurityConstants.INNER);
            if (R.isSuccess(dataR) && dataR.getData() != null) {
                List<DevicePortData> dataList = dataR.getData();
                dataList.forEach(data -> deviceRealData.put(data.getDeviceId(), data));
            }
        }
        List<SiteMonitorDeviceResp> respList = new ArrayList<>(deviceList.size());
        deviceList.forEach(device -> {
            SiteMonitorDeviceResp resp = SiteMonitorConvert.INSTANCE.convertDeviceResp(device);
            resp.setRealData(deviceRealData.get(device.getDeviceId()));
            respList.add(resp);
        });
        return new PageInfo<>(respList);
    }

    @DataScope(orgAlias = "os", userSiteAlias = "us", userOperatorAlias = "uop")
    public List<DevicePortVo> selectDevicePortList(SiteMonitorDeviceReq req) {
        DevicePortDTO reqDTO = new DevicePortDTO()
                .setSiteId(req.getSiteId())
                .setPileName(req.getPileName())
                .setPileSn(req.getPileSn());
        reqDTO.setParams(req.getParams());
        return deviceInfoMapper.selectDevicePorts(reqDTO);
    }
}
