package com.things.cgomp.devicescale.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.devicescale.domain.dto.DeviceInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeviceMapper extends BaseMapper<DeviceInfo> {


    List<DeviceInfo> selectCgPage(DeviceInfo device);
}
