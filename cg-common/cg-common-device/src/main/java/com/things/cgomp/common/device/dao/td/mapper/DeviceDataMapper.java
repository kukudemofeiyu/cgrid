package com.things.cgomp.common.device.dao.td.mapper;

import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import com.things.cgomp.common.device.dao.td.domain.PortStatusData;
import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author things
 */
@Mapper
public interface DeviceDataMapper extends BaseMapperX<DevicePortData> {

    int insertDevicePortDataBatch(@Param(value = "devicePortDataList") List<DevicePortData> devicePortDataList);

    int insertDevicePortStatusBatch(@Param(value = "devicePortStatusList") List<PortStatusData> devicePortStatusList);

}
