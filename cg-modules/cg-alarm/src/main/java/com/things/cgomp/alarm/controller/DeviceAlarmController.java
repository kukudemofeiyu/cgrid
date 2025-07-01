package com.things.cgomp.alarm.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.alarm.dto.DeviceAlarmDTO;
import com.things.cgomp.alarm.service.IDeviceAlarmService;
import com.things.cgomp.alarm.vo.DeviceAlarmVO;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.web.controller.BaseController;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deviceAlarm")
public class DeviceAlarmController extends BaseController {

    @Autowired
    private IDeviceAlarmService deviceAlarmService;

    @RequiresPermissions("device:alarm:list")
    @GetMapping(value = "/page", name = "告警分页列表")
    public R<PageInfo<DeviceAlarmVO>> selectAlarmPage(DeviceAlarmDTO deviceAlarmDTO) {
        PageInfo<DeviceAlarmVO> page = deviceAlarmService.selectAlarmPage(deviceAlarmDTO);
        return R.ok(page);
    }

    @RequiresPermissions("device:alarm:deal")
    @PutMapping(value = "/deal", name = "处理告警")
    public R<?> dealAlarm(@RequestParam(value = "alarmId") Long alarmId){
       Boolean success = deviceAlarmService.dealAlarm(alarmId);
        return R.ok(success);
    }
}
