package com.things.cgomp.common.mq.message;

import com.things.cgomp.common.mq.common.AbstractBody;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 设备网关节点请求对象
 * @author things
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class DeviceGwNodeInfoReq extends AbstractBody {

    /**
     * 节点ID
     */
    private String nodeId;
    /**
     * 节点服务名称
     */
    private String serviceName;
    /**
     * 监听端口号
     */
    private Integer visitPort;
    /**
     * 监听地址
     */
    private String visitIp;
    /**
     * 主题
     */
    private String topic;
    /**
     * 请求路径
     */
    private String contextPath;
    /**
     * broker服务集合
     */
    private List<BrokerInfo> brokerInfoList;
}
