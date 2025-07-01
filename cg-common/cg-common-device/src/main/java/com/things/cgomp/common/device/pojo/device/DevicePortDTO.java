package com.things.cgomp.common.device.pojo.device;

import com.things.cgomp.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DevicePortDTO extends BaseEntity implements Serializable  {
    private static final long serialVersionUID = 1L;

    private Integer component;

    /**
     * 运营商ID
     */
    private Long operatorId;

    /**
     * 充电桩id
     */
    private Long pileId;
    /**
     * 充电桩名称
     */
    private String pileName;
    /**
     * 充电桩编号
     */
    private String pileSn;

    /**
     * 站点ID
     */
    private Long siteId;
}


