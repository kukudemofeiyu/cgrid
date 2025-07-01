package com.things.cgomp.common.mq.common;

import com.things.cgomp.common.mq.message.ServiceInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author things
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Metadata implements Serializable {

    private static final long serialVersionUID = 1L;

    private String requestId;           // 请求ID
    private String txId;                // 事务Id
    private Long eventTime;             // 事件生产时间
    private Long deviceId;              // 终端ID
    private Long portId;                // 枪口ID
    private Integer payloadCode;        // 协议命令字
    private ServiceInfo serviceInfo;

    public String buildNodeId() {
        if (serviceInfo == null) {
            return null;
        }
        return serviceInfo.getNodeId();
    }
}
