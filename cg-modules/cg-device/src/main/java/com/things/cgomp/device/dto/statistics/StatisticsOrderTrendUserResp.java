package com.things.cgomp.device.dto.statistics;

import lombok.Data;

import java.io.Serializable;

/**
 * @author things
 */
@Data
public class StatisticsOrderTrendUserResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日期
     * 格式：yyyy-MM-dd
     */
    private String date;
    /**
     * 用户数量
     */
    private Long userCount;
}
