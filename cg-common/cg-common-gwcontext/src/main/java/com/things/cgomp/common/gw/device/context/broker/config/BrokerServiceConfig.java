package com.things.cgomp.common.gw.device.context.broker.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BrokerServiceConfig {
    private String protocolCode;
    private String alias;
    private Integer status;
    private String ruleJson;

}
