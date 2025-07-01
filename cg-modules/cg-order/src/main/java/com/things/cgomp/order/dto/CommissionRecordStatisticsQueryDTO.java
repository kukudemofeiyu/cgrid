package com.things.cgomp.order.dto;

import com.things.cgomp.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class CommissionRecordStatisticsQueryDTO extends BaseEntity {

    /**
     * 运营商ID
     */
    private Long operatorId;
    /**
     * 站点ID
     */
    private Long siteId;
    /**
     * 时间类型
     * 1按月查询 2按年查询
     */
    private Integer dateType;
    /**
     * 时间格式
     */
    private String dateFormat;
    /**
     * 开始时间
     */
    private String beginTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 数据类型
     * 1运营商统计 2站点统计
     */
    private Integer type;
}
