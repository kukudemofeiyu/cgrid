package com.things.cgomp.order.queue.consumer;

import com.things.cgomp.common.mq.annotation.RocketMQConsumer;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.consumer.AbrRocketMQConsumer;
import com.things.cgomp.common.mq.message.DrawGunReqMsg;
import com.things.cgomp.order.service.IOrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Slf4j
@RocketMQConsumer
@RocketMQMessageListener(
        topic = MQTopics.ORDER,
        consumerGroup = MQTopics.Group.ORDER + "-" + MQTopics.Tag.DRAW_GUN_REQ,
        selectorExpression = MQTopics.Tag.DRAW_GUN_REQ
)
public class OrderDrawGunConsumer extends AbrRocketMQConsumer<DrawGunReqMsg> {

    @Resource
    private IOrderInfoService orderInfoService;

    @Override
    protected void onMessage(DrawGunReqMsg reqMsg, Metadata metadata) {
        log.info("OrderDrawGunConsumer 收到拔枪消息, reqMsg={}, metadata={}", reqMsg, metadata);

        if(metadata != null){
            reqMsg.setPileId(metadata.getDeviceId())
                    .setPortId(metadata.getPortId());
        }

        orderInfoService.drawGun(reqMsg);
    }
}
