package com.things.cgomp.device.dto.device;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateChargeGridRuleDTO {

    private Long deviceId;

    /**
     * 计费规则id
     */
    private Long payRuleId;

}
