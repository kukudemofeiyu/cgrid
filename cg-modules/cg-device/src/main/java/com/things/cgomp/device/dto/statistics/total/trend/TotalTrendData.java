package com.things.cgomp.device.dto.statistics.total.trend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author things
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalTrendData<Type> {

    /**
     * key
     */
    private String key;
    /**
     * 值
     */
    private Type value;
}
