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
public class TotalLongData extends TotalData<Long> {

    public TotalLongData(){
        super();
    }

    public TotalLongData(Long value, Long totalValue) {
        super(value, totalValue);
    }

    @Override
    protected Long defaultValue() {
        return 0L;
    }

    @Override
    public void calcFluctuate(Long compareValue) {
        if (compareValue == null || compareValue <= 0) {
            this.fluctuate = BigDecimal.ZERO;
            return;
        }
        BigDecimal valueB = new BigDecimal(value);
        BigDecimal compareB = new BigDecimal(compareValue);
        this.fluctuate = valueB
                .subtract(compareB)
                .divide(compareB, 2, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100L))
                .setScale(2, RoundingMode.HALF_UP);
    }
}