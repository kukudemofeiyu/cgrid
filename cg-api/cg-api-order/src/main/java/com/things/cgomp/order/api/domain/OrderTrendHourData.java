package com.things.cgomp.order.api.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class OrderTrendHourData extends OrderTrendData {

    /**
     * 小时 hh:mm
     */
    private String hour;
}
