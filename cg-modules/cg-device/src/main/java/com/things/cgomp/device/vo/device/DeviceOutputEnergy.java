package com.things.cgomp.device.vo.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 输出电能数据
 * @author things
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceOutputEnergy implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 功率
     */
    private Double power;
    /**
     * 电压
     */
    private Double voltage;
    /**
     * 电流
     */
    private Double current;
}
