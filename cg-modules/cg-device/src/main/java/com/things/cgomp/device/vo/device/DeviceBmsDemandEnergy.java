package com.things.cgomp.device.vo.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * BMS需求电能数据
 * @author things
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceBmsDemandEnergy implements Serializable {

    private static final long serialVersionUID = 1L;

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
