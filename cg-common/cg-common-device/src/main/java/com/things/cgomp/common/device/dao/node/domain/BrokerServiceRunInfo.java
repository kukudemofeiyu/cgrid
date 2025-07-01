package com.things.cgomp.common.device.dao.node.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
@TableName("device_node_broker_run_info")
public class BrokerServiceRunInfo {

    private Long id;

    private String serviceId;

    private Integer brokerId;

    private String brokerName;

    private Integer servicePort;

    /**
     * 1初始化 2初始化完成 3运行中 4停止
     */
    private Integer state;

    private Date updateTime;
}
