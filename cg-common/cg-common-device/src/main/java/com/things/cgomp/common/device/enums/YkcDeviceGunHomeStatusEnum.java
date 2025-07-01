package com.things.cgomp.common.device.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 充电枪归位状态
 */
@Getter
@AllArgsConstructor
public enum YkcDeviceGunHomeStatusEnum {
    /**
     * 归位
     */
    HOMING("归位", 1, "充电枪已归位"),
    /**
     * 未归位
     */
    NOT_HOME("未归位", 0, "充电枪未归位"),
    /**
     * 未知
     */
    UNKNOWN("未知", 2, "充电枪归位状态未知");

    private final String statusName;
    private final Integer status;
    private final String desc;

    public static YkcDeviceGunHomeStatusEnum getStatus(Integer status){
        YkcDeviceGunHomeStatusEnum[] values = YkcDeviceGunHomeStatusEnum.values();
        for (YkcDeviceGunHomeStatusEnum value : values) {
            if (value.getStatus().equals(status)) {
                return value;
            }
        }
        return null;
    }
}
