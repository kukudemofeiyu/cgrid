package com.things.cgomp.device.dto.monitor;

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
public class SiteMonitorDeviceReq extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 站点ID
     */
    private Long siteId;
    /**
     * 充电桩名称
     */
    private String pileName;
    /**
     * 充电桩编码
     */
    private String pileSn;
}
