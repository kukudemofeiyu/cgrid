package com.things.cgomp.device.vo.device.trend;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 电量曲线
 * @author things
 */
@Data
@Accessors(chain = true)
public class ElectricityTrend implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 电量
     */
    private List<TrendData<Double>> electricity = new ArrayList<>();
}
