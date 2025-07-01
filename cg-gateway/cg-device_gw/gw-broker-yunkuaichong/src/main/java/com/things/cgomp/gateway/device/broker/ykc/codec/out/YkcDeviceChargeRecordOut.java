package com.things.cgomp.gateway.device.broker.ykc.codec.out;

import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 充电记录
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class YkcDeviceChargeRecordOut extends YkcMessageOut implements Serializable {
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 确认
     */
    private Boolean ack;

    public static YkcDeviceChargeRecordOut of(Integer frameSerialNo, String orderNo, Boolean ack) {
        return new YkcDeviceChargeRecordOut(frameSerialNo, orderNo, ack);
    }

    private YkcDeviceChargeRecordOut(Integer frameSerialNo, String orderNo, Boolean ack) {
        super(frameSerialNo, DeviceOpConstantEnum.CHARGE_RECORD_RESP.getOpCode(), true);
        this.orderNo = orderNo;
        this.ack = ack;
    }

}
