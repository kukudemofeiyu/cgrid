package com.things.cgomp.order.queue.consumer;

import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.consumer.AbrRocketMQConsumer;
import com.things.cgomp.common.mq.message.DeviceDataReqMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;

/**
 * 订单设备数据消息消费者
 *
 * @author things
 */
@Slf4j
/*@RocketMQConsumer*/
@RocketMQMessageListener(
        topic = MQTopics.DEVICE_DATA,
        consumerGroup = MQTopics.Group.DEVICE_DATA + "-" + MQTopics.ORDER
)
public class OrderDeviceDataConsumer extends AbrRocketMQConsumer<DeviceDataReqMsg> {

    @Override
    protected void onMessage(DeviceDataReqMsg reqMsg, Metadata metadata) {
            // TODO、拔枪重新插枪，重新生成订单
    }
}
