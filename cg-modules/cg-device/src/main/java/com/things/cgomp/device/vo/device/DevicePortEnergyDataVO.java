package com.things.cgomp.device.vo.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 充电枪电能数据
 * @author things
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DevicePortEnergyDataVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 输入电能数据
     */
    private Object inputEnergy;
    /**
     * 输出电能数据
     */
    private DeviceOutputEnergy outputEnergy;
    /**
     * BMS需求数据
     */
    private DeviceBmsDemandEnergy bmsEnergy;
}
