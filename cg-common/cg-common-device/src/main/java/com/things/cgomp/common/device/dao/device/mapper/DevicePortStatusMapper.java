package com.things.cgomp.common.device.dao.device.mapper;

import com.things.cgomp.common.device.dao.device.domain.DevicePortStatus;
import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author things
 */
@Mapper
public interface DevicePortStatusMapper extends BaseMapperX<DevicePortStatus> {

    int saveOrUpdateStatus(DevicePortStatus devicePortStatus);

    DevicePortStatus selectDevicePortStatus(@Param("portId") Long portId);

    DevicePortStatus selectDevicePortStatusDetail(@Param("portId") Long portId);

    int updateVin(DevicePortStatus devicePortStatus);
}
