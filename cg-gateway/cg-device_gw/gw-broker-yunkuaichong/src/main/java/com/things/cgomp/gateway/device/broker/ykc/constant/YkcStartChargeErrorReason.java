package com.things.cgomp.gateway.device.broker.ykc.constant;

/**
 * 控制充电错误原因
 */
public interface YkcStartChargeErrorReason {
    String NONE = "无";
    /**
     * 设备编号不匹配
     */
    String DEVICE_NO_NOT_MATCH = "设备编号不匹配";
    /**
     * 已在充电中
     */
    String ALREADY_CHARGING = "已在充电中";
    /**
     * 设备故障
     */
    String DEVICE_FAILURE = "设备故障";
    /**
     * 充电枪离线
     */
    String GUN_DEVICE_OFF_LINE = "充电枪离线";
    /**
     * 未插枪
     */
    String NOT_INSERTED = "未插枪";
    /**
     * 未知错误
     */
    String UNKNOWN = "未知错误";
}
