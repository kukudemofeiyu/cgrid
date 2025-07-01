package com.things.cgomp.system.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author things
 */
@Data
@Accessors(chain = true)
public class AppUserTrendQueryDTO {

    /**
     * 开始日期
     */
    private String beginDate;
    /**
     * 结束日期
     */
    private String endDate;
}
