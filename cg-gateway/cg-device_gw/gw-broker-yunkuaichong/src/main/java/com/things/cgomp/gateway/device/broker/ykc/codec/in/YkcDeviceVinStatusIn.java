package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class YkcDeviceVinStatusIn extends YkcMessageIn{

    /**
     * 桩编号
     */
    private String deviceNo;

    /**
     * 枪号
     */
    private String gunNo;

    /**
     * VIN 码
     */
    private String vin;

    /**
     * 当前时间
     */
    private LocalDateTime vinTs;


    public static YkcDeviceVinStatusIn of(String deviceNo, String gunNo, String vin, LocalDateTime ts) {
         return new YkcDeviceVinStatusIn(deviceNo, gunNo,vin,ts);
    }

    private YkcDeviceVinStatusIn(String deviceNo, String gunNo, String vin, LocalDateTime ts) {
        this.deviceNo = deviceNo;
        this.gunNo = gunNo;
        this.vin = vin;
        this.vinTs = ts;
    }
}
