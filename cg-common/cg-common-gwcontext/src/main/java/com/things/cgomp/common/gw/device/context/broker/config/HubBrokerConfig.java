package com.things.cgomp.common.gw.device.context.broker.config;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class HubBrokerConfig {
    private Boolean start;
    private Integer brokerId;
    private String name;
    private String serviceBean;
    private Integer keepaliveTime;
    /**
     * 异常离线时间（分钟），当最后活跃时间+异常离线时间>当前时间，判定离线
     */
    private Integer abnormalOffLineTime;
    private boolean taskCheck;

    private Integer port;

    /**
     * 会话建立后,当设备有消息过来时,每间隔多久时间同步一下设备的状态。单位：分钟。
     */
    private Integer syncSessionIntervalTime;

    private String configInfo;

    private String protocol;

    private ServiceNodeInfo hubNodeInfo;

}
