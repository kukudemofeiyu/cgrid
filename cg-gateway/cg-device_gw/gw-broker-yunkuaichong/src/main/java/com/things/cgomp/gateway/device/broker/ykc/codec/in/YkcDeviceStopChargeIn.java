package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import lombok.Data;

import java.io.Serializable;

/**
 * 设备停止充电
 */
@Data
public class YkcDeviceStopChargeIn extends YkcMessageIn implements Serializable {
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 枪号
     */
    private String gunNo;
    /**
     * 控制成功
     */
    private Boolean controlSuccessful;
    /**
     * 错误原因
     */
    private String errorReason;

    public static YkcDeviceStopChargeIn of(String deviceNo, String gunNo, Boolean controlSuccessful,
                                           String errorReason) {
        return new YkcDeviceStopChargeIn(deviceNo, gunNo, controlSuccessful, errorReason);
    }

    private YkcDeviceStopChargeIn(String deviceNo, String gunNo, Boolean controlSuccessful,
                                  String errorReason) {
        this.deviceNo = deviceNo;
        this.gunNo = gunNo;
        this.controlSuccessful = controlSuccessful;
        this.errorReason = errorReason;
    }
}
