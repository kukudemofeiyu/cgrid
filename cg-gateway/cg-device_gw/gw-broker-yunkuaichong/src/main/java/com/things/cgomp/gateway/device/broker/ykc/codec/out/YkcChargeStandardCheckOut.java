package com.things.cgomp.gateway.device.broker.ykc.codec.out;

import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 充电标准校验
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class YkcChargeStandardCheckOut extends YkcMessageOut {
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 计费模型编号
     */
    private Integer chargeStandardModelNo;
    /**
     * 校验结果
     */
    private Boolean ack;

    public YkcChargeStandardCheckOut(Integer frameSerialNo, String deviceNo, Integer chargeStandardModelNo, Boolean ack) {
        super(frameSerialNo, DeviceOpConstantEnum.CHARGE_STANDARD_CHECK_RESP.getOpCode(),true);
        this.deviceNo = deviceNo;
        this.chargeStandardModelNo = chargeStandardModelNo;
        this.ack = ack;
    }
}
