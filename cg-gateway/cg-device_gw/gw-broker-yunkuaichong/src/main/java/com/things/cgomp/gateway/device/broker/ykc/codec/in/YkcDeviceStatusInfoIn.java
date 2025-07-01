package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import com.things.cgomp.gateway.device.broker.ykc.constant.YkcDeviceGunStatusEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备状态信息
 */
@Data
public class YkcDeviceStatusInfoIn extends YkcMessageIn implements Serializable {
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
     * 充电枪状态
     */
    private YkcDeviceGunStatusEnum gunStatus;
    /**
     * 枪是否归位
     */
    private Integer gunHomeStatus;
    /**
     * 是否插枪
     */
    private Integer gunInserted;
    /**
     * 电压
     */
    private Double voltage;
    /**
     * 电流
     */
    private Double current;
    /**
     * 枪线温度
     */
    private Integer gunLineTemp;
    /**
     * 枪线编号
     */
    private Long gunLineNo;
    /**
     * soc
     */
    private Integer soc;
    /**
     * 电池组最高温度
     */
    private Integer batteryGroupMaxTemp;
    /**
     * 充电时间
     */
    private Integer chargeTime;
    /**
     * 剩余时间
     */
    private Integer remainingTime;
    /**
     * 充电电量
     */
    private Double chargeElectricity;
    /**
     * 计损充电电量
     */
    private Integer lossChargeElectricity;
    /**
     * 充电金额
     */
    private Double chargeAmount;

    private Integer deviceTemp;

    private Integer smokeDetectorStatus;

    private Double meterValue;

    public static YkcDeviceStatusInfoIn of(String orderNo, String deviceNo, String gunNo,
                                           YkcDeviceGunStatusEnum gunStatus, Integer gunHomeStatus,
                                           Integer gunInserted, Double voltage,
                                           Double current, Integer gunLineTemp,
                                           Long gunLineNo, Integer soc, Integer batteryGroupMaxTemp,
                                           Integer chargeTime, Integer remainingTime, Double chargeElectricity,
                                           Integer lossChargeElectricity, Double chargeAmount,
                                           Integer deviceTemp, Integer smokeDetectorStatus, Double meterValue) {
        return new YkcDeviceStatusInfoIn(orderNo, deviceNo, gunNo, gunStatus, gunHomeStatus,
                gunInserted, voltage, current, gunLineTemp, gunLineNo, soc,
                batteryGroupMaxTemp, chargeTime, remainingTime, chargeElectricity,
                lossChargeElectricity, chargeAmount,deviceTemp,smokeDetectorStatus,meterValue);
    }

    private YkcDeviceStatusInfoIn(String orderNo, String deviceNo, String gunNo,
                                  YkcDeviceGunStatusEnum gunStatus, Integer gunHomeStatus,
                                  Integer gunInserted, Double voltage,
                                  Double current, Integer gunLineTemp,
                                  Long gunLineNo, Integer soc, Integer batteryGroupMaxTemp,
                                  Integer chargeTime, Integer remainingTime, Double chargeElectricity,
                                  Integer lossChargeElectricity, Double chargeAmount,
                                  Integer deviceTemp, Integer smokeDetectorStatus, Double meterValue) {
        this.orderNo = orderNo;
        this.deviceNo = deviceNo;
        this.gunNo = gunNo;
        this.gunStatus = gunStatus;
        this.gunHomeStatus = gunHomeStatus;
        this.gunInserted = gunInserted;
        this.voltage = voltage;
        this.current = current;
        this.gunLineTemp = gunLineTemp;
        this.gunLineNo = gunLineNo;
        this.soc = soc;
        this.batteryGroupMaxTemp = batteryGroupMaxTemp;
        this.chargeTime = chargeTime;
        this.remainingTime = remainingTime;
        this.chargeElectricity = chargeElectricity;
        this.lossChargeElectricity = lossChargeElectricity;
        this.chargeAmount = chargeAmount;
        this.deviceTemp =deviceTemp;
        this.smokeDetectorStatus =smokeDetectorStatus;
        this.meterValue = meterValue;
    }
}
