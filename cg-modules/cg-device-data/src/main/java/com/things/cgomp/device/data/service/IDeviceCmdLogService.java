package com.things.cgomp.device.data.service;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.device.pojo.device.DeviceCmdLogVo;

import java.time.LocalDateTime;
import java.util.Date;

public interface IDeviceCmdLogService {


    PageInfo<DeviceCmdLogVo> selectDeviceCmdLogPage(Long deviceId, Date startTime, Date endTime,String cmd, Integer current, Integer pageSize);
}
