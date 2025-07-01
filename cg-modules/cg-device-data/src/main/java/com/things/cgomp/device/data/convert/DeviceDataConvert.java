package com.things.cgomp.device.data.convert;

import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.message.DeviceChargeDataMsg;
import com.things.cgomp.common.mq.message.DeviceDataReqMsg;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author things
 */
@Mapper
public interface DeviceDataConvert {

    DeviceDataConvert INSTANCE = Mappers.getMapper(DeviceDataConvert.class);

    @Mappings({
            @Mapping(source = "orderNo", target = "orderSn")
    })
    DevicePortData convert(DeviceDataReqMsg reqMsg);

    @Mappings({
            @Mapping(source = "voltage", target = "realVoltage"),
            @Mapping(source = "current", target = "realCurrent"),
            @Mapping(source = "temperature", target = "realTemperature"),
            @Mapping(source = "power", target = "realPower"),
            @Mapping(source = "portSn", target = "portId"),
            @Mapping(source = "amount", target = "totalCost"),
    })
    DeviceChargeDataMsg convertCharge(DeviceDataReqMsg reqMsg);

    default DevicePortData convert(DeviceDataReqMsg reqMsg, Metadata metadata) {
        DevicePortData devicePortData = convert(reqMsg);
        devicePortData.setDeviceId(metadata.getPortId());
        devicePortData.setTs(metadata.getEventTime());
        //devicePortData.setDt(HistoryDataUtil.buildDt(reqMsg.getOrderNo()));
        return devicePortData;
    }
}
