package com.things.cgomp.common.device.pojo.device.push;

import com.things.cgomp.common.device.pojo.device.DeviceCommandEnum;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class PushInfo implements Serializable {

    /**
     * 事务
     */
    private String transactionId;

    /**
     *桩id,通信id, 对应session里面的deviceId
     */
    private Long connectId;

    private Long portId;

    /**
     * 枪编号
     */
    private String gunNo;
    /**
     * 桩编号
     */
    private String deviceNo;

    private Object context;

    private Integer brokerId;

    /**
     * 请求时间
     */
    private Long eventTime;

    private DeviceCommandEnum deviceCommand;

}
