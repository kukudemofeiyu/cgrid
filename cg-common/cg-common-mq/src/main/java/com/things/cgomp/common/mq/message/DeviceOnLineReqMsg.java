package com.things.cgomp.common.mq.message;

import com.things.cgomp.common.mq.common.AbstractBody;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 上线消息请求对象
 * @author things
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class DeviceOnLineReqMsg extends AbstractBody {

    private Long deviceId;
    /**
     * 上线时间
     */
    private Long onLineTime;
    /**
     * 过期时间
     */
    private Long validTime;
    /**
     * 节点ID
     */
    private String nodeId;
    /**
     * broker Id
     */
    private Integer brokerId;
}
