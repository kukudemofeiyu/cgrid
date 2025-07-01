package com.things.cgomp.job.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 站点统计水位线数据
 * @author things
 */
@Data
public class SiteStatisticsWatermark implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 最新一次时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date latestTime;
}
