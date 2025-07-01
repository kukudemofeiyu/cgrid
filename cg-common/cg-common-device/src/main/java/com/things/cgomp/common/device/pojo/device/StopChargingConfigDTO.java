package com.things.cgomp.common.device.pojo.device;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StopChargingConfigDTO  extends CommandBaseConfig {

    /**
     * 充电枪设备编号，app入参
     */
    private Long portId;

}
