package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 设备二维码
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YkcDeviceQrcodeIn extends YkcMessageIn implements Serializable {
    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 设备编号
     */
    private String gunNo;
    /**
     * 下发结果
     */
    private Boolean qrCodeResult;
}
