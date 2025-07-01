package com.things.cgomp.common.device.dao.statistics.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 站点数据统计(时) device_site_statistics_hour
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("device_site_statistics_hour")
public class DeviceSiteStatisticsHour extends DeviceSiteStatistics {

    /**
     * 统计时间
     * hh
     */
    private String statisticsTime;
}
