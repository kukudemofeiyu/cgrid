package com.things.cgomp.gateway.device.broker.ykc.codec.out;

import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 设备登录out
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class YkcDeviceLoginOut extends YkcMessageOut  {
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 登录结果
     */
    private Boolean loginResult;
    /**
     * 最新的RSA公钥
     */
    private String rsaPublicKey;

    public YkcDeviceLoginOut(Integer frameSerialNo, String deviceNo, Boolean loginResult) {
        super(frameSerialNo, DeviceOpConstantEnum.CONNECT_RESP.getOpCode(),false);
        this.deviceNo = deviceNo;
        this.loginResult = loginResult;
    }

    public YkcDeviceLoginOut(Integer frameSerialNo, String deviceNo, Boolean loginResult, String rsaPublicKey) {
        super(frameSerialNo, DeviceOpConstantEnum.CONNECT_RESP.getOpCode(),true);
        this.deviceNo = deviceNo;
        this.loginResult = loginResult;
        this.rsaPublicKey = rsaPublicKey;
    }
}
