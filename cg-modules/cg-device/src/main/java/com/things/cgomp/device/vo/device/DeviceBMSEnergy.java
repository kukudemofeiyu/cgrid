package com.things.cgomp.device.vo.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * BMS电能数据
 * @author things
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceBMSEnergy implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * BMS需求电压
     */
    private Double demandVoltage;
    /**
     * BMS需求电流
     */
    private Double demandCurrent;
    /**
     * BMS监测电压
     */
    private Double checkVoltage;
    /**
     * BMS监测电流
     */
    private Double checkCurrent;
}
