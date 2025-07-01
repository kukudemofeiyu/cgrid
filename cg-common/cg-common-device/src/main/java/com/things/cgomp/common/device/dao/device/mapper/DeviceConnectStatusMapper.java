package com.things.cgomp.common.device.dao.device.mapper;

import com.things.cgomp.common.device.dao.device.domain.DeviceConnectStatus;
import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import com.things.cgomp.common.mybatisplus.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author things
 */
@Mapper
public interface DeviceConnectStatusMapper extends BaseMapperX<DeviceConnectStatus> {

    default DeviceConnectStatus selectByDeviceId(Long deviceId){
        LambdaQueryWrapperX<DeviceConnectStatus> wrapper = new LambdaQueryWrapperX<DeviceConnectStatus>()
                .eq(DeviceConnectStatus::getDeviceId, deviceId);
        return selectOne(wrapper);
    }
}
