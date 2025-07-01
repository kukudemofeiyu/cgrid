package com.things.cgomp.order.queue.consumer;

import com.things.cgomp.common.mq.annotation.RocketMQConsumer;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.consumer.AbrRocketMQConsumer;
import com.things.cgomp.common.mq.message.order.EndChargingReqMsg;
import com.things.cgomp.order.service.IOrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 订单结束充电消费
 *
 */
@Slf4j
@RocketMQConsumer
@RocketMQMessageListener(
        topic = MQTopics.ORDER,
        consumerGroup = MQTopics.Group.ORDER + "-" + MQTopics.Tag.ORDER_END_CHARGING,
        selectorExpression = MQTopics.Tag.ORDER_END_CHARGING
)
public class OrderEndChargingConsumer extends AbrRocketMQConsumer<EndChargingReqMsg> {


    @Resource
    private IOrderInfoService orderInfoService;

    @Override
    protected void onMessage(EndChargingReqMsg reqMsg, Metadata metadata) {
        log.info("OrderEndChargingConsumer 收到结束充电消息, reqMsg={}, metadata={}", reqMsg, metadata);

        orderInfoService.endCharging(reqMsg);
    }
}
