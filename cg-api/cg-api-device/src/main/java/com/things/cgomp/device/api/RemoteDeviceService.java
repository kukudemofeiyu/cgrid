package com.things.cgomp.device.api;

import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.device.pojo.device.DeviceCount;
import com.things.cgomp.device.api.dto.UpdateChargeGridRulesDTO;
import com.things.cgomp.device.api.factory.RemoteDeviceFallbackFactory;
import com.things.cgomp.device.api.model.vo.ChargingAppPortDetailVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(contextId = "remoteDeviceService",
        value = ServiceNameConstants.SYSTEM_DEVICE,
//        url = "http://localhost:9011",
        fallbackFactory = RemoteDeviceFallbackFactory.class)
public interface RemoteDeviceService {
    @GetMapping(value = "/devicePort/appPort", name = "查看app枪口带桩带站点详情")
     R<ChargingAppPortDetailVO> getAppChargePort(@RequestParam(value = "portId") Long portId,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
    @GetMapping(value = "/devicePort/appPortBySn", name = "查看app枪口带桩带站点详情-根据桩编码")
     R<ChargingAppPortDetailVO> getAppChargePortBySn(@RequestParam(value = "portSn") String portSn,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @GetMapping(value = "/deviceGrid/deviceMap", name = "查询设备map")
    R<Map<Long,DeviceInfo>> selectDeviceMap(
            @RequestParam(value = "deviceIds") List<Long> deviceIds
    );

    @GetMapping(value = "/deviceGrid/device", name = "查询设备")
    R<DeviceInfo> selectDevice(
            @RequestParam(value = "deviceId") Long deviceId
    );

    @PostMapping(value = "/devicePort/insertGun",name = "插枪")
    R<String> insertGun(
            @RequestParam("portId") Long portId,
            @RequestParam("insertTime") Long insertTime,
            @RequestParam("vin") String vin
    );

    @PutMapping(value = "/deviceGrid/rules", name = "批量修改规则")
    R<?> updateRules(
            @RequestBody UpdateChargeGridRulesDTO rule
    );

    @GetMapping(value = "/deviceGrid/deviceCount", name = "获取充电桩数量")
    R<DeviceCount> getPileDeviceCount(@RequestParam(value = "operatorId", required = false) Long operatorId,
                                      @RequestParam(value = "siteId", required = false) Long siteId,
                                      @RequestParam(value = "sn", required = false) String sn,
                                      @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
