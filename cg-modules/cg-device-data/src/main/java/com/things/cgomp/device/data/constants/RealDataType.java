package com.things.cgomp.device.data.constants;

import com.things.cgomp.common.device.dao.td.domain.BasePersistData;
import com.things.cgomp.common.device.dao.td.domain.DeviceBmsDemandData;
import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import lombok.Getter;

/**
 * 设备实时数据类型
 * @author things
 */
@Getter
public enum RealDataType {

    MONITOR("monitor", DevicePortData.class, "实时监测数据"),
    BMS_DEMAND("bmsDemand", DeviceBmsDemandData.class, "BMS需求与充电机输出"),
    BMS_INFO("bmsInfo", null, "充电过程BMS信息"),
    ;

    private final String tag;
    private final Class<? extends BasePersistData> classT;
    private final String desc;

    RealDataType(String tag, Class<? extends BasePersistData> classT, String desc) {
        this.tag = tag;
        this.classT = classT;
        this.desc = desc;
    }

    public static RealDataType getByTag(String tag){
        RealDataType[] values = RealDataType.values();
        for (RealDataType value : values) {
            if (value.getTag().equals(tag)) {
                return value;
            }
        }
        return null;
    }
}
