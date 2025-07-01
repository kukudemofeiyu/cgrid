package com.things.cgomp.common.device.pojo.device;

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
public class ChargeGridTreeReqDTO extends BaseEntity {

    /**
     * 设备ID
     */
    private Long deviceId;
}
