package com.things.cgomp.device.data.convert;

import com.things.cgomp.common.device.dao.td.domain.DeviceBmsDemandData;
import com.things.cgomp.common.device.dao.td.domain.DeviceChargeData;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.message.DeviceChargeDataReqMsg;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author things
 */
@Mapper
public interface DeviceChargeDataConvert {

    DeviceChargeDataConvert INSTANCE = Mappers.getMapper(DeviceChargeDataConvert.class);

    @Mappings({
            @Mapping(source = "bmsOVoltageXQ", target = "bmsDemandVoltage"),
            @Mapping(source = "bmsOCurrentXQ", target = "bmsDemandCurrent"),
            @Mapping(source = "bmsOChargeMode", target = "bmsChargeMode"),
            @Mapping(source = "bmsOVoltageMeasure", target = "bmsCheckVoltage"),
            @Mapping(source = "bmsOCurrentMeasure", target = "bmsCheckCurrent"),
            @Mapping(source = "bmsOMaxVoltage", target = "bmsMaxCellVoltage"),
            @Mapping(source = "bmsOMaxVoltageGroupNo", target = "bmsMaxCellVoltageGroupNo"),
            @Mapping(source = "bmsOSoc", target = "bmsSoc"),
            @Mapping(source = "bmsOChargeTime", target = "bmsLeftChargeTime"),
            @Mapping(source = "bmsOVoltageOutput", target = "bmsPileOutputVoltage"),
            @Mapping(source = "bmsOCurrentOutput", target = "bmsPileOutputCurrent"),
            @Mapping(source = "bmsOTotalChargeTime", target = "bmsTotalChargeTime"),
    })
    DeviceChargeData convert(DeviceChargeDataReqMsg bean);

    DeviceBmsDemandData convertDemandResp(DeviceChargeData bean);

    List<DeviceBmsDemandData> convertDemandRespList(List<DeviceChargeData> list);

    default DeviceChargeData convertToPersist(DeviceChargeDataReqMsg bean, Metadata metadata) {
        DeviceChargeData data = convert(bean);
        data.setOrderSn(bean.getOrderSn());
        data.setDeviceId(metadata.getPortId());
        data.setTs(metadata.getEventTime());
        return data;
    }
}
