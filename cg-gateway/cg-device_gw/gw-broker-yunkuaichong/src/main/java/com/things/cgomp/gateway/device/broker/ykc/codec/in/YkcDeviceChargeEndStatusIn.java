package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import lombok.Data;

@Data
public class YkcDeviceChargeEndStatusIn extends YkcMessageIn{

    /**
     * 交易流水号
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


    private Integer bmsEndSoc;

    private Double bmsMinCurrent;

    private Double bmsMaxCurrent;

    private Integer bmsLowTemp;

    private Integer bmsHighTemp;

    private Integer totalChargeTime;

    private Double deviceElectricity;

    private Integer chargeDeviceNo;

    public static YkcDeviceChargeEndStatusIn of(String orderNo, String deviceNo, String gunNo, Integer bmsEndSoc,
                                                Double bmsMinCurrent, Double bmsMaxCurrent, Integer bmsLowTemp,
                                                Integer bmsHighTemp, Integer totalChargeTime, Double deviceElectricity,
                                                Integer chargeDeviceNo) {
        return new YkcDeviceChargeEndStatusIn(orderNo,  deviceNo,  gunNo,  bmsEndSoc,  bmsMinCurrent,
                bmsMaxCurrent,  bmsLowTemp,  bmsHighTemp,  totalChargeTime,  deviceElectricity,  chargeDeviceNo);
    }


    private YkcDeviceChargeEndStatusIn(String orderNo, String deviceNo, String gunNo, Integer bmsEndSoc,
                                       Double bmsMinCurrent, Double bmsMaxCurrent, Integer bmsLowTemp,
                                       Integer bmsHighTemp, Integer totalChargeTime, Double deviceElectricity,
                                       Integer chargeDeviceNo) {
        this.orderNo = orderNo;
        this.deviceNo = deviceNo;
        this.gunNo = gunNo;
        this.bmsEndSoc = bmsEndSoc;
        this.bmsMinCurrent = bmsMinCurrent;
        this.bmsMaxCurrent = bmsMaxCurrent;
        this.bmsLowTemp = bmsLowTemp;
        this.bmsHighTemp = bmsHighTemp;
        this.totalChargeTime = totalChargeTime;
        this.deviceElectricity = deviceElectricity;
        this.chargeDeviceNo = chargeDeviceNo;
    }
}
