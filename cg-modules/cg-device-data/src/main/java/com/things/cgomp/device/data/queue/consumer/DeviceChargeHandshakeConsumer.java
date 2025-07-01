package com.things.cgomp.device.data.queue.consumer;

import com.things.cgomp.common.device.pojo.device.DevicePortStatusDTO;
import com.things.cgomp.common.mq.annotation.RocketMQConsumer;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.consumer.AbrRocketMQConsumer;
import com.things.cgomp.common.mq.message.DeviceChargeHandshakeReqMsg;
import com.things.cgomp.device.data.service.IDeviceStatusService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;

import javax.annotation.Resource;

/**
 * 设备充电握手数据消费者
 *
 * @author things
 */
@Slf4j
@RocketMQConsumer
@RocketMQMessageListener(
        topic = MQTopics.DEVICE_EXPAND,
        consumerGroup = MQTopics.Group.DEVICE_EXPAND + "-" + MQTopics.Tag.DEVICE_CHARGE_HANDSHAKE,
        selectorExpression = MQTopics.Tag.DEVICE_CHARGE_HANDSHAKE
)
public class DeviceChargeHandshakeConsumer extends AbrRocketMQConsumer<DeviceChargeHandshakeReqMsg> {

    @Resource
    private IDeviceStatusService deviceStatusService;

    @Override
    protected void onMessage(DeviceChargeHandshakeReqMsg reqMsg, Metadata metadata) {
        log.info("收到设备充电握手消息: deviceId={}, portId={}, reqMsg={}", metadata.getDeviceId(), metadata.getPortId(), reqMsg);
        DevicePortStatusDTO reqDTO = DevicePortStatusDTO.builder()
                .portId(metadata.getPortId())
                .vin(reqMsg.getVin())
                .build();
        deviceStatusService.modifyPortVin(reqDTO);
    }
}
