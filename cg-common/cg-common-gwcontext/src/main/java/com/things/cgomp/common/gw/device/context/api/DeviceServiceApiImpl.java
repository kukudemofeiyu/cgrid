package com.things.cgomp.common.gw.device.context.api;

import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.device.dao.device.mapper.DeviceInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class DeviceServiceApiImpl extends DeviceMessageServiceImpl implements IDeviceServiceApi{

    @Resource(name="syncMsgRedisTemplate")
    private SyncMsgTemplate syncMsgTemplate;

    @Autowired
    private DeviceInfoMapper deviceInfoMapper;

    @Override
    public SyncMsgTemplate getSyncMsgTemple() {
        return syncMsgTemplate;
    }

    @Override
    public DeviceInfo getDeviceInfo(DeviceInfo deviceInfo) {
        return deviceInfoMapper.findDeviceInfo(deviceInfo);
    }

    @Override
    public DeviceInfo getDeviceInfoById(Long deviceId) {
        return deviceInfoMapper.selectById(deviceId);
    }

    @Override
    public List<DeviceInfo> getGuns(Long deviceId) {
        return deviceInfoMapper.selectChild(deviceId);
    }
}
