package com.things.cgomp.gateway.device.broker.ykc.service.queue;

import com.things.cgomp.common.device.pojo.device.DeviceCommandEnum;
import com.things.cgomp.common.device.pojo.device.push.PushInfo;
import com.things.cgomp.common.device.pojo.device.push.PushResult;
import com.things.cgomp.common.gw.device.context.rest.IPushService;
import com.things.cgomp.common.mq.annotation.RocketMQConsumer;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.consumer.AbrRocketMQConsumer;
import com.things.cgomp.common.mq.message.order.TradingRecordCallReqMsg;
import com.things.cgomp.gateway.device.broker.ykc.YkcBrokerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 接收交易记录召唤消息
 */
@Slf4j
@RocketMQConsumer
@RocketMQMessageListener(
        topic = "${node.config.topic}",
        consumerGroup = "${rocketmq.consumer.group:GW10}-"  +  MQTopics.Tag.TRADING_RECORD_CALL,
        selectorExpression = MQTopics.Tag.TRADING_RECORD_CALL
)
public class ChargeRecordGetConsumer extends AbrRocketMQConsumer<TradingRecordCallReqMsg> {

    @Autowired
    private YkcBrokerService ykcBrokerService;

    @Override
    protected void onMessage(TradingRecordCallReqMsg reqMsg, Metadata metadata) {
        IPushService pushService = ykcBrokerService.getPushService();
        PushInfo pushInfo = new PushInfo();
        pushInfo.setConnectId(reqMsg.getPileId())
                .setPortId(reqMsg.getPortId())
                .setDeviceCommand(DeviceCommandEnum.getChargeRecord)
                .setEventTime(metadata.getEventTime())
                .setTransactionId(metadata.getTxId())
                .setContext(reqMsg);
        PushResult pushResult = pushService.processPush(pushInfo);

        log.info("交易记录召唤,result={}", pushResult);
    }
}
