package com.things.cgomp.devicescale.session;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
@Getter
@Setter
public class State {
    //0x00 离线
    //0x01 故障
    //0x02 空闲
    //0x03 充电
    private int status;
    private String vin;


    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String orderNo;

    private String lastOrderNo;
    private String lastVin;
    private int lastStatus;


    public void setStatus(int status) {
        this.lastStatus = this.status;
        this.status = status;
    }

    public void setOrderNo(String orderNo) {
        this.lastOrderNo = this.orderNo;
        this.orderNo = orderNo;
    }

    public void setVin(String vin) {
        this.lastVin = this.vin;
        this.vin = vin;
    }

    public void clearOrderInfo() {
        startTime = null;
        endTime = null;
        orderNo = null;
        vin = null;
    }
}
