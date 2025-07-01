package com.things.cgomp.common.device.dao.node.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
@TableName("device_node_info")
public class ServiceInfo {

    private String serviceId;

    private String serviceName;

    /**
     * 端口
     */
    private Integer visitPort;

    /**
     * IP
     */
    private String visitIp;

    /**
     * 上下文路径
     */
    private String contextPath;

    /**
     *最后活跃时间
     */
    private Date updateTime;

    private String topic;


}
