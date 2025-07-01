package com.things.cgomp.common.device.dao.td.mapper;

import com.things.cgomp.common.device.dao.td.domain.DeviceCmdLogData;
import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import com.things.cgomp.common.device.dao.td.domain.PortStatusData;
import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author things
 */
@Mapper
public interface DeviceCmdLogMapper extends BaseMapperX<DeviceCmdLogData> {

    int insertDeviceCmdLogDataBatch(@Param(value = "deviceCmdLogList") List<DeviceCmdLogData> deviceCmdLogData);

    List<DeviceCmdLogData> selectDeviceCmdLog(@Param("deviceId") Long deviceId,
                                              @Param("startTime") Date startTime,
                                              @Param("endTime")Date endTime,
                                              @Param("cmd")String cmd);
}
