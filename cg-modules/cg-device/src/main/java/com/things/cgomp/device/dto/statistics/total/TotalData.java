package com.things.cgomp.device.dto.statistics.total;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author things
 */
@Data
public abstract class TotalData<Type> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前值
     */
    protected Type value;
    /**
     * 累计值
     */
    protected Type totalValue;
    /**
     * 浮动值
     * 整数为向上浮动，复数为向下浮动
     */
    protected BigDecimal fluctuate = BigDecimal.ZERO;

    public TotalData(){

    }

    public TotalData(Type totalValue){
        if (totalValue != null) {
            this.totalValue = totalValue;
        } else {
            this.totalValue = defaultValue();
        }
    }

    public TotalData(Type value, Type totalValue){
        if (value != null) {
            setValue(value);
        } else {
            this.value = defaultValue();
        }
        if (totalValue != null) {
            setTotalValue(totalValue);
        } else {
            this.totalValue = defaultValue();
        }
    }

    protected Type defaultValue() {
        return null;
    }

    public void calcFluctuate(Type compareValue){

    }
}
