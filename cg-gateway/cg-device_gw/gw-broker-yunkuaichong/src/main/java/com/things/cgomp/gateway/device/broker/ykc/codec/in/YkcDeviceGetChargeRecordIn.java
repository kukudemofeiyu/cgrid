package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class YkcDeviceGetChargeRecordIn extends YkcMessageIn{

    private String orderNo;

    private String deviceNo;

    private String gunNo;

    /**
     * 召唤状态
     */
    private Boolean controlSuccessful;

    /**
     * 失败原因
     */
    private String  errorReason;
}
