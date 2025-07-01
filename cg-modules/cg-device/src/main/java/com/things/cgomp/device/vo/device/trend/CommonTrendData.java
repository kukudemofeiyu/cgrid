package com.things.cgomp.device.vo.device.trend;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 电压/电流曲线
 * @author things
 */
@Data
@Accessors(chain = true)
public class CommonTrendData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 输出电压/输出电流
     */
    private List<TrendData<Double>> output = new ArrayList<>();
    /**
     * 检测电压/检测电流
     */
    private List<TrendData<Double>> check = new ArrayList<>();
    /**
     * 需求电压/需求电流
     */
    private List<TrendData<Double>> demand = new ArrayList<>();
}
