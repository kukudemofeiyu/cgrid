package com.things.cgomp.order.queue.consumer;

import com.things.cgomp.common.mq.annotation.RocketMQConsumer;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.consumer.AbrRocketMQConsumer;
import com.things.cgomp.common.mq.message.OrderPaySuccessReqMsg;
import com.things.cgomp.order.service.IOrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;

import javax.annotation.Resource;

/**
 * 订单结算消息消费者
 *
 * @author things
 */
@Slf4j
@RocketMQConsumer
@RocketMQMessageListener(
        topic = MQTopics.ORDER,
        consumerGroup = MQTopics.Group.ORDER + "-" + MQTopics.Tag.ORDER_PAY_SUCCESS,
        selectorExpression = MQTopics.Tag.ORDER_PAY_SUCCESS
)
public class OrderPaySuccessConsumer extends AbrRocketMQConsumer<OrderPaySuccessReqMsg> {

    @Resource
    private IOrderInfoService orderInfoService;

    @Override
    protected void onMessage(OrderPaySuccessReqMsg reqMsg, Metadata metadata) {
        log.info("OrderPaySuccessConsumer 收到订单支付成功消息, reqMsg={}, metadata={}", reqMsg, metadata);

        orderInfoService.updateOrderPayStatus(
                reqMsg
        );
    }
}
