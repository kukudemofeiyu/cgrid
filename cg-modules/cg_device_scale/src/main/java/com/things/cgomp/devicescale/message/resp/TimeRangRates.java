package com.things.cgomp.devicescale.message.resp;

import com.things.cgomp.devicescale.annotation.Property;
import com.things.cgomp.devicescale.message.DataType;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
public class TimeRangRates {
    private Integer price;

    @Property(index = 1, type = DataType.BYTE, length = 1, desc = "时段费率号")
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


}
