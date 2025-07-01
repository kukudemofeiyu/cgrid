package com.things.cgomp.gateway.device.broker.ykc.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 设备停止充电错误原因
 */
@Getter
@AllArgsConstructor
public enum YkcStopChargeErrorReasonEnum {
    /**
     * 无
     */
    NONE("NONE", "无"),
    /**
     * 设备编号不匹配
     */
    DEVICE_NO_NOT_MATCH("DEVICE_NO_NOT_MATCH", "设备编号不匹配"),
    /**
     * 未充电
     */
    NOT_CHARGING("NOT_CHARGING", "枪未处于充电状态"),
    /**
     * 其他
     */
    OTHER("OTHER", "其他");
    private final String reason;
    private final String reasonDesc;
}
