package com.things.cgomp.common.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {

    public static BigDecimal format(BigDecimal decimal) {
        if(decimal == null){
            return null;
        }

        return BigDecimal.valueOf(decimal.doubleValue());
    }

    public static BigDecimal getTwoDecimal(Double value) {
        if (value==null){
            return null;
        }
        BigDecimal decimal = new BigDecimal(value);
        return decimal.setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal sum(
            BigDecimal a,
            BigDecimal b
    ) {
        if (a == null && b == null) {
            return null;
        }

        if (a == null) {
            return b;
        }

        if (b == null) {
            return a;
        }

        return a.add(b);
    }
}

