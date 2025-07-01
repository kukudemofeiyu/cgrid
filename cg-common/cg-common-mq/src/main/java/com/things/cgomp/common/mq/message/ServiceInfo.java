package com.things.cgomp.common.mq.message;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author things
 */
@Data
@Builder
public class ServiceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nodeId;

    private String serviceName;

    private String topic;
}
