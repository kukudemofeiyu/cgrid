package com.things.cgomp.device.data.queue.consumer;

import com.things.cgomp.common.device.dao.td.domain.DeviceCmdLogData;
import com.things.cgomp.common.mq.annotation.RocketMQConsumer;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.consumer.AbrRocketMQConsumer;
import com.things.cgomp.common.mq.message.DeviceCmdLogReqMsg;
import com.things.cgomp.device.data.convert.DeviceCmdLogConvert;
import com.things.cgomp.device.data.persistence.queue.DeviceCmdLogPersistMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 设备交互日志数据消费者
 *
 * @author things
 */
@Slf4j
@RocketMQConsumer
@RocketMQMessageListener(
        topic = MQTopics.CMD_lOG,
        consumerGroup = MQTopics.Group.CMD_lOG
)
public class DeviceCmdLogConsumer extends AbrRocketMQConsumer<DeviceCmdLogReqMsg> {

    @Autowired
    private DeviceCmdLogPersistMessageService cmdLogPersistMessageService;

    @Override
    protected void onMessage(DeviceCmdLogReqMsg reqMsg, Metadata metadata) {

        DeviceCmdLogData convert = DeviceCmdLogConvert.INSTANCE.convertToPersist(reqMsg, metadata);
        cmdLogPersistMessageService.persist(convert);

    }


}
