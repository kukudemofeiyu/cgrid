package com.things.cgomp.devicescale.message.resp;

import com.things.cgomp.devicescale.annotation.Property;
import com.things.cgomp.devicescale.annotation.Type;
import com.things.cgomp.devicescale.message.DataType;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
public class ModeRate {


    private Long energyPrice;


    private Long servicePrice;

    @Property(index = 0, type = DataType.UNSIGNED_INT_LE, length = 4, desc = "电费费率")
    public Long getEnergyPrice() {
        return energyPrice;
    }

    @Property(index = 4, type = DataType.UNSIGNED_INT_LE, length = 4, desc = "服务费费率")
    public Long getServicePrice() {
        return servicePrice;
    }

    public void setEnergyPrice(Long energyPrice) {
        this.energyPrice = energyPrice;
    }


    public void setServicePrice(Long servicePrice) {
        this.servicePrice = servicePrice;
    }

}
