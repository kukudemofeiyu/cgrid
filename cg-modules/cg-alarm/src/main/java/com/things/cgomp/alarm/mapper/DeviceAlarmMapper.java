package com.things.cgomp.alarm.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.alarm.domain.DeviceAlarmInfo;
import com.things.cgomp.alarm.dto.DeviceAlarmDTO;

import java.util.List;

public interface DeviceAlarmMapper extends BaseMapper<DeviceAlarmInfo> {

    DeviceAlarmInfo findAlarmInfo(DeviceAlarmInfo deviceAlarmInfo);

    List<DeviceAlarmInfo> selectAlarmPage(DeviceAlarmDTO deviceAlarmDTO);
}
