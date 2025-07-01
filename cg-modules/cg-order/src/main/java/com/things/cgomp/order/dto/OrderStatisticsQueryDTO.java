package com.things.cgomp.order.dto;

import com.things.cgomp.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class OrderStatisticsQueryDTO extends BaseEntity {

    /**
     * 运营商ID
     */
    private Long operatorId;
    /**
     * 站点ID
     */
    private Long siteId;
    /**
     * 充电桩ID
     */
    private Long pileId;
    /**
     * 充电枪ID
     */
    private Long portId;
    /**
     * 充电桩编号
     */
    private String deviceSn;
    /**
     * 运行状态
     * 1在线 0离线
     */
    private Integer runStatus;
    /**
     * 开始日期
     */
    private String beginTime;
    /**
     * 结束日期
     */
    private String endTime;

    /**
     * 充电桩id
     */
    private List<Long> pileIds;

}
