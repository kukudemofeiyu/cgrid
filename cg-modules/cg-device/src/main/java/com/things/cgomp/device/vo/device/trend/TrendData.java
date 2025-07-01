package com.things.cgomp.device.vo.device.trend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author things
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrendData<Type> {

    /**
     * key
     */
    private String key;
    /**
     * 值
     */
    private Type value;
}
