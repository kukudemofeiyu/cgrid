package com.things.cgomp.device.data.api.factory;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import com.things.cgomp.common.device.pojo.device.DeviceCmdLogVo;
import com.things.cgomp.device.data.api.RemoteDeviceCmdLogService;
import com.things.cgomp.device.data.api.RemoteDeviceDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RemoteDeviceCmdLogFallbackFactory implements FallbackFactory<RemoteDeviceCmdLogService> {

    @Override
    public RemoteDeviceCmdLogService create(Throwable cause) {
        log.error("设备数据服务调用失败:{}", cause.getMessage());
        return new RemoteDeviceCmdLogService() {

            @Override
            public R<PageInfo<DeviceCmdLogVo>> getCmdLogPage(Long deviceId, String startTime, String endTime, String cmd, Integer current, Integer pageSize) {
                return R.fail("查询设备交互日志失败: " + cause.getMessage());
            }
        };
    }
}
