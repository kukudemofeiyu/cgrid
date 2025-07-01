package com.things.cgomp.common.device.dao.statistics.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 站点数据统计(天) device_site_statistics_day
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("device_site_statistics_day")
public class DeviceSiteStatisticsDay extends DeviceSiteStatistics {

}
