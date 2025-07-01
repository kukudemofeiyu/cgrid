package com.things.cgomp.system.queue.consumer;

import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.consumer.AbrRocketMQConsumer;
import com.things.cgomp.common.mq.message.TradingRecordConfirmReqMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;

/**
 * 订单金额消息消费者
 *
 * @author things
 */
@Slf4j
/*@RocketMQConsumer*/
@RocketMQMessageListener(
        topic = MQTopics.ORDER,
        consumerGroup = MQTopics.Group.ORDER + "-" + MQTopics.Tag.ORDER_AMOUNT,
        selectorExpression = MQTopics.Tag.ORDER_AMOUNT
)
public class OrderAmountProcessConsumer extends AbrRocketMQConsumer<TradingRecordConfirmReqMsg> {

    @Override
    protected void onMessage(TradingRecordConfirmReqMsg reqMsg, Metadata metadata) {
        log.info("OrderAmountProcessConsumer 收到订单金额处理消息, reqMsg={}, metadata={}", reqMsg, metadata);

        // TODO、保证幂等性
        // TODO、调用本地service（事务）
        //      1.查询账号余额（版本号）
        //      2.修改账户余额
        //      3.添加余额记录
        //      4.修改订单状态（待确认）
    }
}
