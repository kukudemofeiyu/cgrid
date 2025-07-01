package com.things.cgomp.gateway.device.broker.ykc.service.queue;

import com.things.cgomp.common.device.service.DeviceChargeExceptionService;
import com.things.cgomp.common.mq.annotation.RocketMQConsumer;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.consumer.AbrRocketMQConsumer;
import com.things.cgomp.common.mq.message.order.TradingRecordConfirmRespMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 接收交易记录处理回复消息
 */
@Slf4j
@RocketMQConsumer
@RocketMQMessageListener(
        topic = MQTopics.ORDER,
        consumerGroup = MQTopics.Tag.TRADING_RECORD_CONFIRM_RESP,
        selectorExpression = MQTopics.Tag.TRADING_RECORD_CONFIRM_RESP

)
public class ChargeRecordConfirmRespConsumer extends AbrRocketMQConsumer<TradingRecordConfirmRespMsg>{

    @Autowired
    private DeviceChargeExceptionService deviceChargeExceptionService;

    @Override
    protected void onMessage(TradingRecordConfirmRespMsg reqMsg, Metadata metadata) {
        log.info("收到交易记录处理回复, reqMsg:{}", reqMsg);
        if(reqMsg.getSuccess().equals(200)){
            log.info("交易记录处理成功, reqMsg:{}", reqMsg);

            //从数据库删除
            deviceChargeExceptionService.deleteRecord(reqMsg.getOrderNo());
        }

    }
}
