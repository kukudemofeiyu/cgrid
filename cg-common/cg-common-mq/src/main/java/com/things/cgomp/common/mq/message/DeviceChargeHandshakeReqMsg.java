package com.things.cgomp.common.mq.message;

import com.things.cgomp.common.mq.common.AbstractBody;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备充电握手请求对象
 * @author things
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class DeviceChargeHandshakeReqMsg extends AbstractBody {

    /**
     * 车辆识别码
     */
    private String vin;
}
