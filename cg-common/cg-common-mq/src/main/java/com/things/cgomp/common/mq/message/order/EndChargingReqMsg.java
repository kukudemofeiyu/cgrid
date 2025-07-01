package com.things.cgomp.common.mq.message.order;

import com.things.cgomp.common.mq.common.AbstractBody;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class EndChargingReqMsg extends AbstractBody {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 结束时间
     */
    private Long endTime;

}
