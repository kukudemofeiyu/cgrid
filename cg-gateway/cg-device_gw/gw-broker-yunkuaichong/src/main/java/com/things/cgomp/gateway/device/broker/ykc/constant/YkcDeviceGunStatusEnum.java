package com.things.cgomp.gateway.device.broker.ykc.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 充电枪状态
 */
@Getter
@AllArgsConstructor
public enum YkcDeviceGunStatusEnum {
    /**
     * 离线
     */
    OFFLINE(0x00, "离线"),
    /**
     * 故障
     */
    ERROR(0x01, "故障"),
    /**
     * 空闲
     */
    IDLE(0x02, "空闲"),
    /**
     * 充电
     */
    CHARGING(0x03, "充电");


    private final Integer status;
    private final String statusName;
}
