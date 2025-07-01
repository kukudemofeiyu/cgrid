package com.things.cgomp.device.controller;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.device.pojo.device.StartChargingConfigDTO;
import com.things.cgomp.common.device.pojo.device.StopChargingConfigDTO;
import com.things.cgomp.device.service.IDeviceCommandService;
import com.things.cgomp.common.device.pojo.device.DeviceCommandVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 下发指令控制层
 */
@RestController
@RequestMapping("/command")
@AllArgsConstructor
@Slf4j
public class DeviceCommandController {


    @Autowired
    private IDeviceCommandService deviceCommandService;

    @PostMapping(value = "/startCharging", name = "启动充电")
    public R<DeviceCommandVO> startCharging(
            @RequestBody StartChargingConfigDTO startChargingConfig
    ) {
        DeviceCommandVO response = deviceCommandService.startCharging(startChargingConfig);
        return R.ok(response);
    }

    @PostMapping(value = "/stopCharging", name = "停止充电")
    public R<DeviceCommandVO> stopCharging(
            @RequestBody StopChargingConfigDTO stopChargingConfig
    ) {
        DeviceCommandVO response = deviceCommandService.stopCharging(stopChargingConfig);
        return R.ok(response);
    }
}
