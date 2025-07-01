package com.things.cgomp.alarm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.alarm.domain.DeviceAlarmInfo;
import com.things.cgomp.alarm.dto.DeviceAlarmDTO;
import com.things.cgomp.alarm.vo.DeviceAlarmVO;

public interface IDeviceAlarmService extends IService<DeviceAlarmInfo> {
    PageInfo<DeviceAlarmVO> selectAlarmPage(DeviceAlarmDTO deviceAlarmDTO);

    Boolean dealAlarm(Long alarmId);
}
