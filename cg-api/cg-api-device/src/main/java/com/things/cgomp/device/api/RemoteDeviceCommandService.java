package com.things.cgomp.device.api;

import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.device.pojo.device.DeviceCommandVO;
import com.things.cgomp.common.device.pojo.device.StartChargingConfigDTO;
import com.things.cgomp.common.device.pojo.device.StopChargingConfigDTO;
import com.things.cgomp.device.api.factory.RemoteDeviceCommandFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId = "remoteDeviceCommandService",
        value = ServiceNameConstants.SYSTEM_DEVICE,
//        url = "http://localhost:9011",
        fallbackFactory = RemoteDeviceCommandFallbackFactory.class)
public interface RemoteDeviceCommandService {
    @PostMapping(value = "/command/startCharging", name = "启动充电")
    public R<DeviceCommandVO> startCharging(@RequestBody StartChargingConfigDTO startChargingConfig);
    @PostMapping(value = "/command/stopCharging", name = "停止充电")
    public R<DeviceCommandVO> stopCharging(@RequestBody StopChargingConfigDTO stopChargingConfig);
}
