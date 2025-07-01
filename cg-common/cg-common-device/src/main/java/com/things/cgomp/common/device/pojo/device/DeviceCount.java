package com.things.cgomp.common.device.pojo.device;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author things
 */
@Data
@Accessors(chain = true)
public class DeviceCount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总数量
     */
    private Long totalCount;
    /**
     * 在线数量
     */
    private Long onlineCount;
    /**
     * 离线数量
     */
    private Long offlineCount;
}
