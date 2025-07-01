package com.things.cgomp.device.service;

import com.things.cgomp.common.device.dao.device.domain.DevicePortStatus;

/**
 * @author things
 */
public interface IDevicePortStatusDbService {

    /**
     * 查询枪口状态数据
     * @param deviceId 充电枪ID
     * @return DevicePortStatus
     */
    DevicePortStatus selectPortStatus(Long deviceId);
}
