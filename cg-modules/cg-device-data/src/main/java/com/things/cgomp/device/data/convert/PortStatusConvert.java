package com.things.cgomp.device.data.convert;

import com.things.cgomp.common.device.dao.device.domain.DevicePortStatus;
import com.things.cgomp.common.device.dao.device.domain.PortChangeValue;
import com.things.cgomp.common.device.dao.td.domain.PortStatusData;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.message.DrawGunReqMsg;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author things
 */
@Mapper
public interface PortStatusConvert {

    PortStatusConvert INSTANCE = Mappers.getMapper(PortStatusConvert.class);

    @Mappings({
            @Mapping(source = "portId", target = "deviceId"),
            @Mapping(source = "remark", target = "desc")
    })
    PortStatusData convert(DevicePortStatus bean);

    @Mapping(source = "orderSn", target = "orderNo")
    DrawGunReqMsg convertDrawFlag(PortChangeValue bean);

    default DrawGunReqMsg convertToOccupy(PortChangeValue bean, Metadata metadata){
        DrawGunReqMsg reqMsg = convertDrawFlag(bean);
        reqMsg.setPileId(metadata.getDeviceId());
        reqMsg.setPortId(metadata.getPortId());
        reqMsg.setDrawGunTime(metadata.getEventTime());
        return reqMsg;
    }

    default PortStatusData convertToPersist(DevicePortStatus bean, Long eventTime){
        PortStatusData data = convert(bean);
        data.setTs(eventTime);
        return data;
    }
}
