package com.things.cgomp.device.dto.statistics.total.trend;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TotalStringTrend extends TotalTrend<String> {

    public TotalStringTrend(){
        super();
    }

    public TotalStringTrend(String totalValue) {
        super(totalValue);
    }

    public TotalStringTrend(String totalValue, List<TotalTrendData<String>> trends) {
        super(totalValue, trends);
    }

    @Override
    protected String defaultValue() {
        return "";
    }
}
