package com.things.cgomp.common.mq.message;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author things
 */
@Data
@Builder
public class BrokerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer brokerId;

    private String name;

    private Integer state;

    private Integer servicePort;
}
