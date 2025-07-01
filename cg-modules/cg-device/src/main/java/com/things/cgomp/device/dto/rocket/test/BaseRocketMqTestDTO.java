package com.things.cgomp.device.dto.rocket.test;

import com.things.cgomp.common.mq.common.AbstractBody;
import com.things.cgomp.common.mq.common.Metadata;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public abstract class BaseRocketMqTestDTO {

    private String topic;

    private String tag;

    private Metadata metadata;

    public abstract AbstractBody getBody();

}
