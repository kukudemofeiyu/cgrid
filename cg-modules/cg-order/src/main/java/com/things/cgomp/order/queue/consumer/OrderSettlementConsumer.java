package com.things.cgomp.order.queue.consumer;

import com.alibaba.fastjson.JSON;
import com.things.cgomp.common.mq.annotation.RocketMQConsumer;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.consumer.AbrRocketMQConsumer;
import com.things.cgomp.common.mq.message.TradingRecordConfirmReqMsg;
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
        consumerGroup = MQTopics.Group.ORDER + "-" + MQTopics.Tag.TRADING_RECORD_CONFIRM_REQ,
        selectorExpression = MQTopics.Tag.TRADING_RECORD_CONFIRM_REQ
)
public class OrderSettlementConsumer extends AbrRocketMQConsumer<TradingRecordConfirmReqMsg> {

    @Resource
    private IOrderInfoService orderInfoService;

    @Override
    protected void onMessage(TradingRecordConfirmReqMsg reqMsg, Metadata metadata) {
        log.info("OrderSettlementConsumer 收到订单结算消息, reqMsg={}, metadata={}", JSON.toJSONString(reqMsg), metadata);

        reqMsg.setPileId(metadata.getDeviceId())
                .setPortId(metadata.getPortId());
        orderInfoService.confirmTradingRecord(reqMsg);
    }
}
