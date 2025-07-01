package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import lombok.Data;

import java.io.Serializable;

/**
 * 设备登录
 */
@Data
public class YkcDeviceLoginIn extends YkcMessageIn implements Serializable {

    /**
     * 对称算法随机密钥，加密数据
     */
    private String randomKey;

    private String deviceNo;
    /**
     * 交直流类型
     * True： 交流
     * False： 直流
     */
    private Boolean acOrDirectType;
    /**
     * 充电枪数量
     */
    private Integer chargeGunCount;
    /**
     * 通信协议版本
     */
    private String protocolVersion;
    /**
     * 程序版本
     */
    private String programVersion;
    /**
     * 网络类型
     */
    private String networkType;
    /**
     * sim卡
     */
    private String simCard;
    /**
     *  运营商
     */
    private String operatorType;

    public static YkcDeviceLoginIn of(String randomKey, String deviceNo, Boolean acOrDirectType, Integer chargeGunCount,
                                      String protocolVersion, String programVersion, String networkType,
                                      String simCard, String operatorType) {
        return new YkcDeviceLoginIn(randomKey, deviceNo, acOrDirectType, chargeGunCount, protocolVersion, programVersion,
                networkType, simCard, operatorType);
    }

    public YkcDeviceLoginIn(String randomKey, String deviceNo, Boolean acOrDirectType, Integer chargeGunCount,
                            String protocolVersion, String programVersion, String networkType,
                            String simCard, String operatorType) {
        this.randomKey = randomKey;
        this.deviceNo = deviceNo;
        this.acOrDirectType = acOrDirectType;
        this.chargeGunCount = chargeGunCount;
        this.protocolVersion = protocolVersion;
        this.programVersion = programVersion;
        this.networkType = networkType;
        this.simCard = simCard;
        this.operatorType = operatorType;
    }

    private YkcDeviceLoginIn() {}
}
