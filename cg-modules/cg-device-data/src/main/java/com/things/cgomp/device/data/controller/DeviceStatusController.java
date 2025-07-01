package com.things.cgomp.device.data.controller;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.device.dao.device.domain.DevicePortStatus;
import com.things.cgomp.common.device.enums.PortStatusOperate;
import com.things.cgomp.common.device.pojo.device.DevicePortStatusDTO;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.security.annotation.InnerAuth;
import com.things.cgomp.device.data.service.IDeviceStatusService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 设备状态数据控制器
 *
 * @author things
 */
@Log(title = "设备状态数据")
@RestController
@RequestMapping("/status")
public class DeviceStatusController {

    @Resource
    private IDeviceStatusService deviceStatusService;

    /**
     * 内部接口
     * 检查修改充电桩实时状态
     * @param req 请求对象
     * @return Boolean
     */
    @InnerAuth
    @PostMapping("/checkAndModifyPortStatus")
    public R<Boolean> checkAndModifyPortStatus(@RequestBody DevicePortStatusDTO req,
                                               @RequestParam("operate") String operate){
        PortStatusOperate statusOperate = PortStatusOperate.valueOf(operate);
        deviceStatusService.checkAndModifyPortStatus(req, statusOperate);
        return R.ok(true);
    }

    /**
     * 内部接口
     * 获取充电枪实时状态
     * @param portId 充电枪ID
     * @return DevicePortStatusDTO
     */
    @InnerAuth
    @GetMapping("/getPortRealStatus")
    public R<DevicePortStatus> getPortRealStatus(@RequestParam("portId") Long portId){
        DevicePortStatus portStatus = deviceStatusService.getPortRealStatus(portId);
        return R.ok(portStatus);
    }
}
