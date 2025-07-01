package com.things.cgomp.common.mq.message;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author things
 */
@Data
@Builder
public class SyncDeviceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会话最后活跃时间
     */
    private Long lastActiveTime;
    /**
     * 过期时间
     */
    private Long validTime;
    /**
     * 设备ID
     */
    private Long deviceId;

}
