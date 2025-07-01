package com.things.cgomp.common.gw.device.context.broker.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;


@Configuration
@ConfigurationProperties(prefix = "node.config")
public class ServiceNodeInfo {

    private String nodeId;


    private String serviceName;


    private Integer visitPort;


    private String visitIp;

    private String topic;

    private String contextPath;


    public String getNodeId() {
        if (StringUtils.isBlank(nodeId)) {
            this.nodeId = getLocalNodeId();
        }
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    private String getLocalNodeId(){
        StringBuilder nodeB = new StringBuilder("HUB_");
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            if (StringUtils.isNotBlank(hostName)){
                nodeB.append(hostName);
            }else{
                nodeB.append(getVisitIp());
            }
        } catch (Exception e) {
            nodeB.append(getVisitIp());
        }
        return nodeB.toString();
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getVisitPort() {
        return visitPort;
    }

    public void setVisitPort(Integer visitPort) {
        this.visitPort = visitPort;
    }

    public String getVisitIp() {
        if (StringUtils.isBlank(visitIp)) {
            this.visitIp = getLocalHostIp();
        }
        return visitIp;
    }

    private String getLocalHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "127.0.0.1";
        }

    }

    public void setVisitIp(String visitIp) {
        this.visitIp = visitIp;
    }

    public String getTopic() {
        if (StringUtils.isBlank(topic)) {
            this.topic = getNodeId();
        }
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
}
