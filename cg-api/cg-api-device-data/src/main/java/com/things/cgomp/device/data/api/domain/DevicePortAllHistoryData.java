package com.things.cgomp.device.data.api.domain;

import com.things.cgomp.common.device.dao.td.domain.DeviceBmsDemandData;
import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 充电枪全部历史数据
 *
 * @author things
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class DevicePortAllHistoryData implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 实时监测历史数据
     */
    private List<DevicePortData> monitorData;
    /**
     * BMS需求历史数据
     */
    private List<DeviceBmsDemandData> bmsDemandData;

    public DevicePortAllHistoryData(){
        monitorData = new ArrayList<>();
        bmsDemandData = new ArrayList<>();
    }
}
