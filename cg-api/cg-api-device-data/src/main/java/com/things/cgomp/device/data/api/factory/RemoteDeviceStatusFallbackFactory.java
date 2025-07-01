package com.things.cgomp.device.data.api.factory;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.device.dao.device.domain.DevicePortStatus;
import com.things.cgomp.common.device.pojo.device.DevicePortStatusDTO;
import com.things.cgomp.device.data.api.RemoteDeviceStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteDeviceStatusFallbackFactory implements FallbackFactory<RemoteDeviceStatusService> {
    @Override
    public RemoteDeviceStatusService create(Throwable throwable) {
        log.error("调用设备状态服务失败:{}", throwable.getMessage());
        return new RemoteDeviceStatusService() {
            @Override
            public R<Boolean> checkAndModifyPortStatus(DevicePortStatusDTO devicePortStatusDTO, String operate, String source) {
                return R.fail("修改充电枪状态失败: " + throwable.getMessage());
            }

            @Override
            public R<DevicePortStatus> getPortRealStatus(Long portId, String source) {
                return R.fail("查询充电枪实时状态失败: " + throwable.getMessage());
            }
        };
    }
}
