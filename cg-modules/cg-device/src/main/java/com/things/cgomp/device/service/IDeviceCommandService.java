package com.things.cgomp.device.service;

import com.things.cgomp.common.device.pojo.device.StopChargingConfigDTO;
import com.things.cgomp.common.device.pojo.device.StartChargingConfigDTO;
import com.things.cgomp.common.device.pojo.device.DeviceCommandVO;

public interface IDeviceCommandService {


    /**
     * 启动充电
     * @param startChargingConfig
     * @return
     */
    DeviceCommandVO startCharging(StartChargingConfigDTO startChargingConfig);

    /**
     * 结束充电
     * @param stopChargingConfig
     * @return
     */
    DeviceCommandVO stopCharging(StopChargingConfigDTO stopChargingConfig);
}
