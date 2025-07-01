package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import com.things.cgomp.gateway.device.broker.ykc.constant.YkcStartChargeErrorReason;
import lombok.Data;

import java.io.Serializable;

/**
 * 充电控制
 */
@Data
public class YkcDeviceStartChargeIn extends YkcMessageIn implements Serializable {
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 枪号
     */
    private String gunNo;
    /**
     * 控制状态
     */
    private Boolean controlSuccessful;
    /**
     * 错误原因
     * @see  YkcStartChargeErrorReason
     */
    private String errorReason;

    public static YkcDeviceStartChargeIn of(String orderNo, String deviceNo, String gunNo,
                                            Boolean controlSuccessful, String errorReason) {
        return new YkcDeviceStartChargeIn(orderNo, deviceNo, gunNo, controlSuccessful, errorReason);
    }


    private YkcDeviceStartChargeIn(String orderNo, String deviceNo, String gunNo, Boolean controlSuccessful, String errorReason) {
        this.orderNo = orderNo;
        this.deviceNo = deviceNo;
        this.gunNo = gunNo;
        this.controlSuccessful = controlSuccessful;
        this.errorReason = errorReason;
    }
}
