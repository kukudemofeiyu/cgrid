package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class YkcChargeBmsStopIn extends YkcMessageIn{

    private String orderNo;

    private String deviceNo;

    private String gunNo;

    private String bmsStopReasonCode;

    private String bmsStopErrorCode;

//    private String bmsStopErrorCode;

}
