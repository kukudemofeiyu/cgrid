package com.things.cgomp.device.vo.device.trend;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 温度曲线
 * @author things
 */
@Data
@Accessors(chain = true)
public class TemperatureTrendData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 枪线温度
     */
    private List<TrendData<Double>> gunLine = new ArrayList<>();
    /**
     * 电池组最高温度
     */
    private List<TrendData<Double>> batteryMax = new ArrayList<>();
    /**
     * 桩体温度
     */
    private List<TrendData<Double>> pileBody = new ArrayList<>();
}
