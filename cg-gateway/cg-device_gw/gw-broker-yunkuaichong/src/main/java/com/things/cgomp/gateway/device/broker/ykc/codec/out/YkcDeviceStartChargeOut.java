package com.things.cgomp.gateway.device.broker.ykc.codec.out;

import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 充电控制
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class YkcDeviceStartChargeOut extends YkcMessageOut  {
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
     *  卡号
     */
    private String cardNo;

    /**
     * 物理卡号
     */
     private String iccid;

    /**
     * 充电金额
     */
    private Integer balance;

    /**
     * 本次充电当前允许的最大功率
     */
    private Integer maxPower;

    /**
     * SOC限制
     */
    private Integer soc;

    /**
     * 充电电量限制
     */
    private Integer electricQ;


    public static YkcDeviceStartChargeOut of(Integer frameSerialNo, String orderNo,
                                             String deviceNo, String gunNo, String cardNo, Integer balance, String iccid) {
    	return new YkcDeviceStartChargeOut(frameSerialNo, orderNo, deviceNo, gunNo, cardNo, balance, iccid);
    }

    private YkcDeviceStartChargeOut(Integer frameSerialNo, String orderNo,
                                    String deviceNo, String gunNo, String cardNo, Integer balance, String iccid) {
        super(frameSerialNo, DeviceOpConstantEnum.START_CHARGE.getOpCode(),true);
        this.orderNo = orderNo;
        this.deviceNo = deviceNo;
        this.gunNo = gunNo;
        this.cardNo = cardNo;
        this.balance = balance;
        this.iccid = iccid;
    }
}
