package com.things.cgomp.common.core.web.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TrendQueryDTO extends BaseEntity {

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
     * 开始日期
     */
    private String beginDate;
    /**
     * 结束日期
     */
    private String endDate;
}
