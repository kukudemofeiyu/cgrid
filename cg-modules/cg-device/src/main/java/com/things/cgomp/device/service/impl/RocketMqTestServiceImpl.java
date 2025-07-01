package com.things.cgomp.device.service.impl;

import com.things.cgomp.common.mq.common.AbstractBody;
import com.things.cgomp.common.mq.common.QueueMsg;
import com.things.cgomp.common.mq.producer.IProducer;
import com.things.cgomp.device.dto.rocket.test.BaseRocketMqTestDTO;
import com.things.cgomp.device.service.RocketMqTestService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RocketMqTestServiceImpl implements RocketMqTestService {

    @Resource
    private IProducer producer;

    @Override
    public void syncSend(BaseRocketMqTestDTO rocketMqTestDTO) {
        QueueMsg<AbstractBody> queueMsg = QueueMsg.builder()
                .body(rocketMqTestDTO.getBody())
                .metadata(rocketMqTestDTO.getMetadata())
                .build();

        producer.syncSend(
                rocketMqTestDTO.getTopic(),
                rocketMqTestDTO.getTag(),
                queueMsg
        );
    }
}
