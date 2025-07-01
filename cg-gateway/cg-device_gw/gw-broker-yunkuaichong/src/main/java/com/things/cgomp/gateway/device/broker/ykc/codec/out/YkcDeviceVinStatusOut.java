package com.things.cgomp.gateway.device.broker.ykc.codec.out;

import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcMessageIn;
import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class YkcDeviceVinStatusOut extends YkcMessageOut {

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 枪号
     */
    private String gunNo;

    /**
     * 鉴权成功标志
     */
    private Boolean authSuccess;

    /**
     * 交易流水号
     */
    private String orderNo;

    public YkcDeviceVinStatusOut(Integer frameSerialNo, String deviceNo, String gunNo, Boolean authSuccess, String orderNo) {
        super(frameSerialNo, DeviceOpConstantEnum.DEVICE_VIN_REPORT_RESP.getOpCode(), true);
        this.deviceNo = deviceNo;
        this.gunNo = gunNo;
        this.authSuccess = authSuccess;
        this.orderNo = orderNo;

    }
}
