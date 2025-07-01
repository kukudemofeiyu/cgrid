package com.things.cgomp.common.device.pojo.device;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SimpleDeviceVo {

    private Long deviceId;

    private String sn;

    private String name;

    /**
     * 充电类型，0-快充，1-慢充
     */
    private Integer chargeType;

}
