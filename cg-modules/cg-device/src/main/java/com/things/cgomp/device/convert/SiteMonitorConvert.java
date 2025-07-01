package com.things.cgomp.device.convert;

import com.things.cgomp.common.device.pojo.device.DevicePortVo;
import com.things.cgomp.device.dto.monitor.SiteMonitorDeviceResp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author thigns
 */
@Mapper
public interface SiteMonitorConvert {

    SiteMonitorConvert INSTANCE = Mappers.getMapper(SiteMonitorConvert.class);

    SiteMonitorDeviceResp convertDeviceResp(DevicePortVo bean);
}
