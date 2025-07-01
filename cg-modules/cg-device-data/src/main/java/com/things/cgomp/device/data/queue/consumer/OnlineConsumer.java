package com.things.cgomp.device.data.queue.consumer;

import cn.hutool.core.date.DateUtil;
import com.things.cgomp.common.device.dao.device.domain.DeviceConnectStatus;
import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.device.dao.device.mapper.DeviceConnectStatusMapper;
import com.things.cgomp.common.device.dao.device.mapper.DeviceInfoMapper;
import com.things.cgomp.common.device.enums.DeviceNetWorkState;
import com.things.cgomp.common.mq.annotation.RocketMQConsumer;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.consumer.AbrRocketMQConsumer;
import com.things.cgomp.common.mq.message.DeviceOnLineReqMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * 设备上线消息消费者
 * @author things
 */
@Slf4j
@RocketMQConsumer
@RocketMQMessageListener(
        topic = MQTopics.STATUS,
        consumerGroup = MQTopics.Group.STATUS + "-" + MQTopics.Tag.ONLINE,
        selectorExpression = MQTopics.Tag.ONLINE
)
public class OnlineConsumer extends AbrRocketMQConsumer<DeviceOnLineReqMsg> {

    @Resource
    private DeviceConnectStatusMapper deviceConnectStatusMapper;
    @Resource
    private DeviceInfoMapper deviceInfoMapper;

    @Override
    protected void onMessage(DeviceOnLineReqMsg reqMsg, Metadata metadata) {
        log.info("收到设备上线消息, metadata={}, body={}", metadata, reqMsg);
        DeviceConnectStatus connectStatus = deviceConnectStatusMapper.selectByDeviceId(reqMsg.getDeviceId());
        DeviceConnectStatus reqStatus = buildReqStatus(reqMsg, metadata);
        if (connectStatus == null) {
            updateActiveTime(reqMsg, metadata);
            // 添加上线状态
            deviceConnectStatusMapper.insert(reqStatus);
        } else {
            if(!connectStatus.getNodeId().equals(reqMsg.getNodeId())){
                reqStatus.setSessionStartTime(null);
            }
            // 更新上线状态
            deviceConnectStatusMapper.updateById(reqStatus);
        }
    }

    /**
     * 更新激活时间
     */
    private void updateActiveTime(DeviceOnLineReqMsg reqMsg, Metadata metadata) {
        DeviceInfo device = new DeviceInfo()
                .setDeviceId(metadata.getDeviceId())
                .setActiveTime(DateUtil.toLocalDateTime(new Timestamp(reqMsg.getOnLineTime())));
        deviceInfoMapper.updateById(device);
    }

    private DeviceConnectStatus buildReqStatus(DeviceOnLineReqMsg reqMsg, Metadata metadata) {
        return new DeviceConnectStatus()
                .setDeviceId(reqMsg.getDeviceId())
                .setUpdateTime(new Timestamp(metadata.getEventTime()))
                .setSessionStartTime(new Timestamp(reqMsg.getOnLineTime()))
                .setValidTime(new Timestamp(reqMsg.getValidTime()))
                .setStatus(DeviceNetWorkState.onLine.getValue())
                .setNodeId(reqMsg.getNodeId())
                .setBrokerId(reqMsg.getBrokerId());
    }
}
