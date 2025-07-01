package com.things.cgomp.common.device.pojo.device;

import com.things.cgomp.common.core.web.domain.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DeviceCountReqDTO extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 运营商ID
     */
    private Long operatorId;
    /**
     * 站点ID
     */
    private Long siteId;
    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 序列号
     */
    private String sn;

    private Integer component;
}
