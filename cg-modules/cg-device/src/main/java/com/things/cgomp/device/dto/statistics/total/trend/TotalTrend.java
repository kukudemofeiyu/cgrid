package com.things.cgomp.device.dto.statistics.total.trend;

import com.things.cgomp.device.dto.statistics.total.TotalData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TotalTrend<Type> extends TotalData<Type> {

    /**
     * 趋势
     */
    protected List<TotalTrendData<Type>> trends = new ArrayList<>();

    public TotalTrend() {
        super();
    }

    public TotalTrend(Type totalValue) {
        super(totalValue);
    }

    public TotalTrend(Type value, Type totalValue) {
        super(value, totalValue);
    }

    public TotalTrend(Type totalValue, List<TotalTrendData<Type>> trends) {
        super(totalValue);
        this.trends = trends;
    }

    public TotalTrend(Type value, Type totalValue, List<TotalTrendData<Type>> trends) {
        super(value, totalValue);
        this.trends = trends;
    }
}
