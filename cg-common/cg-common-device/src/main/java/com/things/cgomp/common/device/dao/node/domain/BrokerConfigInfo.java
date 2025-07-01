package com.things.cgomp.common.device.dao.node.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("device_node_broker")
public class BrokerConfigInfo {
    private Integer brokerId;
    private String brokerName;

    private String protocol;

    private Integer status;
    private Integer port;

    private String serviceBean;

    private Integer taskCheck;

    private Integer keepaliveTime;

    private Integer abnormalOffLineTime;

    private Integer syncSessionIntervalTime;

    private String configInfo;


}
