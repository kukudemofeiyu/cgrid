package com.things.cgomp.device.data.controller;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.web.controller.BaseController;
import com.things.cgomp.common.device.pojo.device.DeviceCmdLogVo;
import com.things.cgomp.device.data.service.IDeviceCmdLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 设备交互日志报文
 */
@RestController
@RequestMapping("/cmdLog")
public class DeviceCmdLogController extends BaseController {

    @Autowired
    private IDeviceCmdLogService deviceCmdLogService;

    @GetMapping(value = "/page", name = "查询充电桩交互日志")
    public R<PageInfo<DeviceCmdLogVo>> selectDeviceCmdLogPage(
            @RequestParam("deviceId") Long deviceId,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime,
            @RequestParam(value = "cmd",required = false) String cmd,
            @RequestParam("current") Integer current,
            @RequestParam("pageSize") Integer pageSize
            ) {
        PageInfo<DeviceCmdLogVo> page = deviceCmdLogService.selectDeviceCmdLogPage(
                deviceId,
                DateUtil.parse(startTime),
                DateUtil.parse(endTime),
                cmd,
                current,
                pageSize
        );
        return R.ok(page);
    }
}
