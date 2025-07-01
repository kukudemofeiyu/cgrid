package com.things.cgomp.device.data.queue.consumer;

import com.things.cgomp.common.device.dao.device.mapper.DeviceInfoMapper;
import com.things.cgomp.common.mq.annotation.RocketMQConsumer;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.consumer.AbrRocketMQConsumer;
import com.things.cgomp.common.mq.message.DeviceConfirmPayRuleMsg;
import com.things.cgomp.common.mq.producer.IProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 确认更新设备计费模型
 *
 * @author things
 */
@Slf4j
@RocketMQConsumer
@RocketMQMessageListener(
        topic = MQTopics.CONFIRM_MESSAGE,
        consumerGroup = MQTopics.Group.CONFIRM_MESSAGE +"-" + MQTopics.Tag.CONFIRM_PAY_RULE_UPDATE,
        selectorExpression = MQTopics.Tag.CONFIRM_PAY_RULE_UPDATE

)
public class DevicePayRuleConfirmConsumer extends AbrRocketMQConsumer<DeviceConfirmPayRuleMsg> {

    @Autowired
    private DeviceInfoMapper deviceInfoMapper;

    @Override
    protected void onMessage(DeviceConfirmPayRuleMsg reqMsg, Metadata metadata) {
        log.info("收到更新计费规则消息: deviceId={}, reqMsg={}", metadata.getDeviceId(), reqMsg );
        deviceInfoMapper.updateDeviceCurrentPayRule(metadata.getDeviceId(),
                reqMsg.getCurrentPayModelId(),
                reqMsg.getCurrentPayRuleId());
    }

}
