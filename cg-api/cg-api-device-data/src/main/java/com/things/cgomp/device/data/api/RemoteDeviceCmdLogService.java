package com.things.cgomp.device.data.api;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.device.dao.device.domain.DevicePortStatus;
import com.things.cgomp.common.device.pojo.device.DeviceCmdLogVo;
import com.things.cgomp.common.device.pojo.device.DevicePortStatusDTO;
import com.things.cgomp.device.data.api.factory.RemoteDeviceCmdLogFallbackFactory;
import com.things.cgomp.device.data.api.factory.RemoteDeviceStatusFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(contextId = "remoteDeviceCmdLogService",
        value = ServiceNameConstants.SYSTEM_DEVICE_DATA,
//        url = "http://localhost:9017",
        fallbackFactory = RemoteDeviceCmdLogFallbackFactory.class)
public interface RemoteDeviceCmdLogService {

    @GetMapping( "/cmdLog/page")
    R<PageInfo<DeviceCmdLogVo>> getCmdLogPage( @RequestParam("deviceId") Long deviceId,
                                               @RequestParam("startTime") String startTime,
                                               @RequestParam("endTime") String endTime,
                                               @RequestParam("cmd") String cmd,
                                               @RequestParam("current") Integer current,
                                               @RequestParam("pageSize") Integer pageSize);


}
