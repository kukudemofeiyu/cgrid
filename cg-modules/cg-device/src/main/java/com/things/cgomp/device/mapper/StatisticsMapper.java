package com.things.cgomp.device.mapper;

import com.things.cgomp.device.dto.SiteStatisticsQueryDTO;
import com.things.cgomp.device.dto.statistics.StatisticsDevicePortData;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author things
 */
@Mapper
public interface StatisticsMapper {

    StatisticsDevicePortData selectDevicePortCount(SiteStatisticsQueryDTO queryDTO);
}
