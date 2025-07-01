package com.things.cgomp.device.data.convert;

import com.things.cgomp.common.device.dao.td.domain.DeviceCmdLogData;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.message.DeviceCmdLogReqMsg;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author things
 */
@Mapper
public interface DeviceCmdLogConvert {

    DeviceCmdLogConvert INSTANCE = Mappers.getMapper(DeviceCmdLogConvert.class);

    @Mappings({
    })
    DeviceCmdLogData convert(DeviceCmdLogReqMsg bean);

    default DeviceCmdLogData convertToPersist(DeviceCmdLogReqMsg bean,  Metadata metadata){
        DeviceCmdLogData data = convert(bean);
        data.setDeviceId(metadata.getDeviceId());
        data.setPortId(metadata.getPortId());
        data.setTs(metadata.getEventTime());
        data.setUpdateTs(bean.getUpdateTs());
        return data;
    }
}
