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
public class OrderTrendDateData extends OrderTrendData {

    /**
     * 日期 yyyy-mm-dd
     */
    private String date;
}
