package com.things.cgomp.device.dto.rocket.test;

import com.things.cgomp.common.mq.message.DrawGunReqMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DrawGunMqTestDTO extends BaseRocketMqTestDTO {

    private DrawGunReqMsg body;

}
