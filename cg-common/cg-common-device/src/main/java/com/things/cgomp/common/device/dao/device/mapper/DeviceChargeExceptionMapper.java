package com.things.cgomp.common.device.dao.device.mapper;

import com.things.cgomp.common.device.dao.device.domain.DeviceChargeRecordException;
import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author things
 */
@Mapper
public interface DeviceChargeExceptionMapper extends BaseMapperX<DeviceChargeRecordException> {

    Boolean deleteByOrderNo(String orderNo);

    List<DeviceChargeRecordException> queryAll();

    Long findIdByOrderNo(String orderNo);
}
