package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class YkcDeviceAlarmStatusIn extends YkcMessageIn{

    private String deviceNo;

    private String gunNo;

    private Integer alarmType;

    private String alarmCode;
    private String alarmReason;

    private Long ts;




}
