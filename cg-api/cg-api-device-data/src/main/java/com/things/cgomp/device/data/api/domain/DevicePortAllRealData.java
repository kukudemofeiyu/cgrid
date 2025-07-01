package com.things.cgomp.device.data.api.domain;

import com.things.cgomp.common.core.utils.bean.BeanUtils;
import com.things.cgomp.common.device.dao.td.domain.BasePersistData;
import com.things.cgomp.common.device.dao.td.domain.DeviceBmsDemandData;
import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 充电枪全部实时数据
 *
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DevicePortAllRealData extends DevicePortData {

    /**
     * BMS需求电压
     */
    private Double bmsDemandVoltage;
    /**
     * BMS需求电流
     */
    private Double bmsDemandCurrent;
    /**
     * BMS监测电压
     */
    private Double bmsCheckVoltage;
    /**
     * BMS监测电流
     */
    private Double bmsCheckCurrent;

    public void buildMonitor(BasePersistData monitorData) {
        if (!(monitorData instanceof DevicePortData)) {
            return;
        }
        BeanUtils.copyProperties((DevicePortData) monitorData, this);
    }

    public void buildBmsDemand(BasePersistData demandData) {
        if (!(demandData instanceof DeviceBmsDemandData)) {
            return;
        }
        BeanUtils.copyProperties((DeviceBmsDemandData) demandData, this);
    }
}
