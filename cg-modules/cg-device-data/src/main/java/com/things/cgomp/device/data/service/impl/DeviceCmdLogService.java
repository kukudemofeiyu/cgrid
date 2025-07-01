package com.things.cgomp.device.data.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.utils.CollectionUtils;
import com.things.cgomp.common.core.utils.DateUtils;
import com.things.cgomp.common.datasource.annotation.TDEngine;
import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.device.dao.device.mapper.DeviceInfoMapper;
import com.things.cgomp.common.device.dao.td.domain.DeviceCmdLogData;
import com.things.cgomp.common.device.dao.td.mapper.DeviceCmdLogMapper;
import com.things.cgomp.common.device.pojo.device.DeviceCmdLogVo;
import com.things.cgomp.device.data.service.IDeviceCmdLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.things.cgomp.common.core.utils.PageUtils.startPage;


@Service
@TDEngine
public class DeviceCmdLogService implements IDeviceCmdLogService {

    @Autowired
    private DeviceCmdLogMapper deviceCmdLogMapper;


    @Override
    public PageInfo<DeviceCmdLogVo> selectDeviceCmdLogPage(Long deviceId, Date startTime, Date endTime,String cmd, Integer current, Integer pageSize) {

        PageHelper.startPage(current, pageSize);
        List<DeviceCmdLogData> deviceCmdLogData = deviceCmdLogMapper.selectDeviceCmdLog(deviceId, startTime, endTime,cmd);

        PageInfo<DeviceCmdLogData> deviceCmdLogDataPageInfo = new PageInfo<>(deviceCmdLogData);
        PageInfo<DeviceCmdLogVo> deviceCmdLogVoPageInfo = new PageInfo<>();

        BeanUtils.copyProperties(deviceCmdLogDataPageInfo,deviceCmdLogVoPageInfo );

        List<DeviceCmdLogData> list = deviceCmdLogDataPageInfo.getList();
        if (list != null && list.size() > 0) {
            List<DeviceCmdLogVo> deviceCmdLogVoList = list.stream().map(deviceCmdLogData1 -> {
                DeviceCmdLogVo deviceCmdLogVo = new DeviceCmdLogVo();
                deviceCmdLogVo.setDeviceId(deviceCmdLogData1.getDeviceId());
                deviceCmdLogVo.setCmd(deviceCmdLogData1.getCmdDesc() + "(0x" + deviceCmdLogData1.getCmd() + ")");
                deviceCmdLogVo.setContent(deviceCmdLogData1.getBody());
                deviceCmdLogVo.setEventTs(DateUtils.toDate(deviceCmdLogData1.getTs()));
                deviceCmdLogVo.setSerialNo(deviceCmdLogData1.getSerialNo());
                deviceCmdLogVo.setUpType(deviceCmdLogData1.getUpDown());
                deviceCmdLogVo.setDesc(getDesc(deviceCmdLogData1));

                return deviceCmdLogVo;
            }).collect(Collectors.toList());
            deviceCmdLogVoPageInfo.setList(deviceCmdLogVoList);
            return deviceCmdLogVoPageInfo;
        }

        return new PageInfo<>(Collections.emptyList());
    }

    private String getDesc(DeviceCmdLogData deviceCmdLogData1) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(deviceCmdLogData1.getSecret()!=null ?"AES密钥: "+ deviceCmdLogData1.getSecret() : "" )
                .append(" ")
                .append(deviceCmdLogData1.getOriginHex()!=null  ?  "原始报文: " + deviceCmdLogData1.getOriginHex() : "");
        return stringBuffer.toString();
    }
}
