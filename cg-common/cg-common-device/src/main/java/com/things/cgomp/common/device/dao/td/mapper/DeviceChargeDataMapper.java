package com.things.cgomp.common.device.dao.td.mapper;

import com.things.cgomp.common.device.dao.td.domain.DeviceChargeData;
import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author things
 */
@Mapper
public interface DeviceChargeDataMapper extends BaseMapperX<DeviceChargeData> {

    int insertChargeDataBatch(@Param(value = "deviceChargeData") List<DeviceChargeData> deviceChargeData);

}
