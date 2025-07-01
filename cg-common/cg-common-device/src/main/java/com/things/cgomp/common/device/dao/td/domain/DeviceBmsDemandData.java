package com.things.cgomp.common.device.dao.td.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DeviceBmsDemandData extends BasePersistData {

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
}
