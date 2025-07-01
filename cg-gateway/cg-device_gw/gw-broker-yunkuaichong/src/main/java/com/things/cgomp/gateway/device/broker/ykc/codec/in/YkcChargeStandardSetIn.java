package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class YkcChargeStandardSetIn extends YkcMessageIn {

    private String deviceNo;

    private Boolean controlSuccessful;

}
