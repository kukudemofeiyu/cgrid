package com.things.cgomp.common.mq.message.app;

import com.things.cgomp.common.mq.common.AbstractBody;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AppEndChargingReqMsg extends AbstractBody {
    /**
     * 订单ID
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单类型：0-充电订单 1-预约订单 2-占位订单
     */
    private Integer orderType;

}
