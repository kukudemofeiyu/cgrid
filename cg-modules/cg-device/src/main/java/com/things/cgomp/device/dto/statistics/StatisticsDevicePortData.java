package com.things.cgomp.device.dto.statistics;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 充电枪统计响应数据
 * @author things
 */
@Data
@Accessors(chain = true)
public class StatisticsDevicePortData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总数量
     */
    private Long totalCount;
    /**
     * 离线数量
     */
    private Long offlineCount;
    /***
     * 故障数量
     */
    private Long faultCount;
    /**
     * 空闲数量
     */
    private Long freeCount;
    /**
     * 已插枪数量
     */
    private Long insertedCount;
    /**
     * 充电中数量
     */
    private Long chargingCount;
    /**
     * 占用数量
     */
    private Long occupyCount;
}
