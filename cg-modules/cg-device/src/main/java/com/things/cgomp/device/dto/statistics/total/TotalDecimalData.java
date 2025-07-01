package com.things.cgomp.device.dto.statistics.total;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TotalDecimalData extends TotalData<BigDecimal> {

    public TotalDecimalData() {
        super();
    }

    public TotalDecimalData(BigDecimal value, BigDecimal totalValue) {
        super(value, totalValue);
    }

    @Override
    protected BigDecimal defaultValue() {
        return BigDecimal.ZERO;
    }

    @Override
    public void calcFluctuate(BigDecimal compareValue) {
        if (compareValue == null || compareValue.compareTo(BigDecimal.ZERO) <= 0) {
            this.fluctuate = BigDecimal.ZERO;
            return;
        }
        this.fluctuate = value
                .subtract(compareValue)
                .divide(compareValue, 2, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100L))
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public void setValue(BigDecimal value) {
        this.value = value.setScale(2, RoundingMode.DOWN);
    }

    @Override
    public void setTotalValue(BigDecimal value) {
        this.totalValue = value.setScale(2, RoundingMode.DOWN);
    }
}
