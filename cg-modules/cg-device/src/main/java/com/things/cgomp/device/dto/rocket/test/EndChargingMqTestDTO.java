package com.things.cgomp.device.dto.rocket.test;

import com.things.cgomp.common.mq.message.order.EndChargingReqMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class EndChargingMqTestDTO extends BaseRocketMqTestDTO {

    private EndChargingReqMsg body;

}
