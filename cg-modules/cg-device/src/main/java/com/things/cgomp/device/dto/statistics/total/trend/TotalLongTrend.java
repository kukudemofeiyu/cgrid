package com.things.cgomp.device.dto.statistics.total.trend;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TotalLongTrend extends TotalTrend<Long> {

    public TotalLongTrend(){
        super();
    }

    public TotalLongTrend(Long totalValue) {
        super(totalValue);
    }

    public TotalLongTrend(Long totalValue, List<TotalTrendData<Long>> trends) {
        super(totalValue, trends);
    }

    @Override
    protected Long defaultValue() {
        return 0L;
    }
}
