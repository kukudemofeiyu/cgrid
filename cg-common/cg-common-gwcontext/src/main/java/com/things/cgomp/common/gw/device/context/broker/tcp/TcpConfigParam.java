package com.things.cgomp.common.gw.device.context.broker.tcp;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class TcpConfigParam {
    private String bindIp;
    private Integer bindPort;
    private UnpackRule unpackRule;

    private Integer bossGroupThreadCount;
    private Integer workGroupThreadCount;

    private String leak_detector_level;
    private boolean so_keep_alive;
    private Integer max_pay_load_size;

    private MessageProcessHandler msgProcessHandler;




}
