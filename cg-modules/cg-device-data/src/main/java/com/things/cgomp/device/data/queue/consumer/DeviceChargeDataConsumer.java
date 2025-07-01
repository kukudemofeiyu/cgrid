package com.things.cgomp.device.data.queue.consumer;

import com.things.cgomp.common.device.dao.td.domain.DeviceChargeData;
import com.things.cgomp.common.device.dao.td.domain.DeviceCmdLogData;
import com.things.cgomp.common.mq.annotation.RocketMQConsumer;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.consumer.AbrRocketMQConsumer;
import com.things.cgomp.common.mq.message.DeviceChargeDataReqMsg;
import com.things.cgomp.device.data.convert.DeviceChargeDataConvert;
import com.things.cgomp.device.data.convert.DeviceCmdLogConvert;
import com.things.cgomp.device.data.persistence.queue.DeviceChargeDataMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author things
 */
@Slf4j
@RocketMQConsumer
@RocketMQMessageListener(
        topic = MQTopics.DEVICE_CHARGE_DATA,
        consumerGroup = MQTopics.Group.DEVICE_CHARGE_DATA + MQTopics.Tag.DEVICE_CHARGE,
        selectorExpression =  MQTopics.Tag.DEVICE_CHARGE
)
public class DeviceChargeDataConsumer extends AbrRocketMQConsumer<DeviceChargeDataReqMsg> {

    @Autowired
    private DeviceChargeDataMessageService deviceChargeDataMessageService;

    @Override
    protected void onMessage(DeviceChargeDataReqMsg reqMsg, Metadata metadata) {

        DeviceChargeData convert = DeviceChargeDataConvert.INSTANCE.convertToPersist(reqMsg, metadata);

        deviceChargeDataMessageService.persist(convert);

        //写入redis实时数据库

    }


}
