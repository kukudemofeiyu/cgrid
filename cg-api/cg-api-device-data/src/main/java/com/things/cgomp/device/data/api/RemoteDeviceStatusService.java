package com.things.cgomp.device.data.api;

import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.device.dao.device.domain.DevicePortStatus;
import com.things.cgomp.common.device.pojo.device.DevicePortStatusDTO;
import com.things.cgomp.device.data.api.factory.RemoteDeviceStatusFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(contextId = "remoteDeviceStatusService",
        value = ServiceNameConstants.SYSTEM_DEVICE_DATA,
        //url = "http://localhost:9017",
        fallbackFactory = RemoteDeviceStatusFallbackFactory.class)
public interface RemoteDeviceStatusService {

    /**
     * 修改充电枪实时状态
     * @param devicePortStatusDTO  请求对象
     * @param operate 操作类型
     * @see com.things.cgomp.common.device.enums.PortStatusOperate
     * @return Boolean
     */
    @PostMapping( "/status/checkAndModifyPortStatus")
    R<Boolean> checkAndModifyPortStatus(@RequestBody DevicePortStatusDTO devicePortStatusDTO,
                                        @RequestParam("operate") String operate,
                                        @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 查询充电枪实时状态
     * @param portId 充电枪ID
     * @return DevicePortStatusDTO
     */
    @GetMapping("/status/getPortRealStatus")
    R<DevicePortStatus> getPortRealStatus(@RequestParam("portId") Long portId,
                                          @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
