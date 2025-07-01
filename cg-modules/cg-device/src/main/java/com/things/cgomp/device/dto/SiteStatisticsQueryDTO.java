package com.things.cgomp.device.dto;

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
public class SiteStatisticsQueryDTO extends BaseEntity {

    /**
     * 站点ID
     */
    private Long siteId;
}
