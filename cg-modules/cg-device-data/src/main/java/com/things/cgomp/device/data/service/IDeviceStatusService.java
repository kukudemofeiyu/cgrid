package com.things.cgomp.device.data.service;

import com.things.cgomp.common.device.dao.device.domain.DevicePortStatus;
import com.things.cgomp.common.device.enums.PortStatusOperate;
import com.things.cgomp.common.device.pojo.device.DevicePortStatusDTO;

/**
 * @author things
 */
public interface IDeviceStatusService {

    /**
     * 检查和修改枪口状态
     * @param portStatusDTO    请求对象
     * @param operate          状态操作
     */
    void checkAndModifyPortStatus(DevicePortStatusDTO portStatusDTO, PortStatusOperate operate);

    /**
     * 获取充电枪实时数据
     * @param portId 充电枪ID
     * @return DevicePortStatus
     */
    DevicePortStatus getPortRealStatus(Long portId);

    /**
     * 修改枪口VIN号
     * @param portStatusDTO 请求对象
     */
    void modifyPortVin(DevicePortStatusDTO portStatusDTO);
}
