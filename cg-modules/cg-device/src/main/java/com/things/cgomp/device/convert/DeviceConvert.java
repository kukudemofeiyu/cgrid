package com.things.cgomp.device.convert;

import com.things.cgomp.common.core.utils.StatisticsUtils;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.device.dao.device.domain.DevicePortStatus;
import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import com.things.cgomp.common.device.pojo.device.DeviceDTO;
import com.things.cgomp.device.data.api.domain.DevicePortAllRealData;
import com.things.cgomp.device.vo.device.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author things
 */
@Mapper
public interface DeviceConvert {

    DeviceConvert INSTANCE = Mappers.getMapper(DeviceConvert.class);

    DeviceGridDetailVO convertGridDetail(DeviceDTO bean);

    DeviceSimpleTreeVO convertSimpleTree_2(DeviceDTO bean);

    @Mappings({
            @Mapping(source = "portId", target = "deviceId"),
            @Mapping(source = "portStatusStartTime", target = "statusStartTime", qualifiedByName = "timestampToDate"),
            @Mapping(source = "portStatusDuration", target = "statusDuration", qualifiedByName = "hourToStr")
    })
    DevicePortRealDataVO convertPortRealData(DevicePortStatus bean);

    DeviceOutputEnergy convertOutputEnergy(DevicePortData bean);

    DeviceBmsDemandEnergy convertBmsDemand(DevicePortAllRealData bean);

    default void fillPortRealData(DevicePortRealDataVO realData, DevicePortData bean){
        realData.setSoc(bean.getSoc());
        realData.setChargeEnergy(bean.getChargeEnergy());
        realData.setTimeCharge(bean.getTimeCharge());
        realData.setTimeLeft(bean.getTimeLeft());
        realData.setAmount(bean.getAmount());
    }

    default void fillPortEnergyData(DevicePortEnergyDataVO energyData, DevicePortAllRealData allData){
        // 输出电能数据
        DeviceOutputEnergy outputEnergy = convertOutputEnergy(allData);
        energyData.setOutputEnergy(outputEnergy);
        // BMS需求数据
        DeviceBmsDemandEnergy demandEnergy = convertBmsDemand(allData);
        energyData.setBmsEnergy(demandEnergy);
        // 输入电流电压

    }

    default DeviceSimpleTreeVO convertSimpleTree(DeviceDTO bean){
        DeviceSimpleTreeVO resp = convertSimpleTree_2(bean);
        if(StringUtils.isEmpty(bean.getSn()) && StringUtils.isNotEmpty(bean.getAliasSn())){
            resp.setSn(bean.getAliasSn());
        }
        return resp;
    }

    default List<DeviceSimpleTreeVO> convertSimpleTreeList(List<DeviceDTO> bean){
        if (bean == null) {
            return null;
        }
        List<DeviceSimpleTreeVO> list = new ArrayList<>();
        for (DeviceDTO deviceDTO : bean) {
            list.add(convertSimpleTree(deviceDTO));
        }
        return list;
    }

    @Named("timestampToDate")
    default Date timestampToDate(Long timestamp){
        if (timestamp == null) {
            return null;
        }
        return new Date(timestamp);
    }

    @Named("hourToStr")
    default String hourToStr(BigDecimal hour){
        return StatisticsUtils.buildChargeTime(hour);
    }
}
