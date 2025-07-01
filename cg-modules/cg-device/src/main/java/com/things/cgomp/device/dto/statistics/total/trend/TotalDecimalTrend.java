package com.things.cgomp.device.dto.statistics.total.trend;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TotalDecimalTrend extends TotalTrend<BigDecimal> {

    public TotalDecimalTrend(){
        super();
    }

    public TotalDecimalTrend(BigDecimal totalValue) {
        super(totalValue);
    }

    public TotalDecimalTrend(BigDecimal totalValue, List<TotalTrendData<BigDecimal>> trends) {
        super(totalValue, trends);
    }

    @Override
    protected BigDecimal defaultValue() {
        return BigDecimal.ZERO;
    }

    @Override
    public void setValue(BigDecimal value) {
        this.value = value.setScale(2, RoundingMode.DOWN);;
    }

    @Override
    public void setTotalValue(BigDecimal value) {
        this.totalValue = value.setScale(2, RoundingMode.DOWN);;
    }
}
