package com.things.cgomp.device.service.impl;

import com.things.cgomp.common.device.dao.device.domain.DevicePortStatus;
import com.things.cgomp.common.device.dao.device.mapper.DevicePortStatusMapper;
import com.things.cgomp.device.service.IDevicePortStatusDbService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author things
 */
@Service
public class DevicePortStatusDbServiceImpl implements IDevicePortStatusDbService {

    @Resource
    private DevicePortStatusMapper portStatusMapper;

    @Override
    public DevicePortStatus selectPortStatus(Long deviceId) {
        return portStatusMapper.selectDevicePortStatusDetail(deviceId);
    }
}
