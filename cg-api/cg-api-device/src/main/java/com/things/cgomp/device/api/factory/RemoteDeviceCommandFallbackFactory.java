package com.things.cgomp.device.api.factory;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.device.pojo.device.DeviceCommandVO;
import com.things.cgomp.common.device.pojo.device.StartChargingConfigDTO;
import com.things.cgomp.common.device.pojo.device.StopChargingConfigDTO;
import com.things.cgomp.device.api.RemoteDeviceCommandService;
import com.things.cgomp.device.api.RemoteDeviceService;
import com.things.cgomp.device.api.model.vo.ChargingAppPortDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class RemoteDeviceCommandFallbackFactory implements FallbackFactory<RemoteDeviceCommandService> {
    @Override
    public RemoteDeviceCommandService create(Throwable throwable) {
        log.error("设备指令下发调用失败:{}", throwable.getMessage());
        return new RemoteDeviceCommandService() {

            @Override
            public R<DeviceCommandVO> startCharging(StartChargingConfigDTO startChargingConfig) {
                return R.fail("远程服务调用失败: " + throwable.getMessage());
            }

            @Override
            public R<DeviceCommandVO> stopCharging(StopChargingConfigDTO stopChargingConfig) {
                return R.fail("远程服务调用失败: " + throwable.getMessage());
            }
        };
    }
}
