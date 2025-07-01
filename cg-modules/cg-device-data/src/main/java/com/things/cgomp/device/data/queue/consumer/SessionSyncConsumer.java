package com.things.cgomp.device.data.queue.consumer;

import com.things.cgomp.common.device.dao.device.domain.DeviceConnectStatus;
import com.things.cgomp.common.device.dao.device.mapper.DeviceConnectStatusMapper;
import com.things.cgomp.common.device.enums.DeviceNetWorkState;
import com.things.cgomp.common.mq.annotation.RocketMQConsumer;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.consumer.AbrRocketMQConsumer;
import com.things.cgomp.common.mq.message.DeviceSessionSyncReqMsg;
import com.things.cgomp.common.mq.message.SyncDeviceInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 设备状态更新消息消费者
 * @author things
 */
@Slf4j
@RocketMQConsumer
@RocketMQMessageListener(
        topic = MQTopics.STATUS,
        consumerGroup = MQTopics.Group.STATUS + "-" + MQTopics.Tag.SESSION_SYNC,
        selectorExpression = MQTopics.Tag.SESSION_SYNC
)
public class SessionSyncConsumer extends AbrRocketMQConsumer<DeviceSessionSyncReqMsg> {

    @Resource
    private DeviceConnectStatusMapper deviceConnectStatusMapper;

    @Override
    protected void onMessage(DeviceSessionSyncReqMsg reqMsg, Metadata metadata) {
        log.info("sessionSyncServiceReq:metadata={}, reqMsg={}", metadata,reqMsg);

        Long startTime = System.currentTimeMillis();
        reqMsg.getDeviceInfos()
                .forEach(syncDeviceInfo -> {
                    DeviceConnectStatus reqStatus = buildReqStatus(syncDeviceInfo, metadata);
                    deviceConnectStatusMapper.updateById(reqStatus);
                });

        Long endTime = System.currentTimeMillis();
        long total = endTime - startTime;
        log.info("更新会话耗时：{}", total);
    }

    private DeviceConnectStatus buildReqStatus(SyncDeviceInfo syncDeviceInfo, Metadata metadata){
        Date updateTime = new Date(syncDeviceInfo.getLastActiveTime());
        return new DeviceConnectStatus()
                .setDeviceId(syncDeviceInfo.getDeviceId())
                .setStatus(DeviceNetWorkState.onLine.getValue())
                .setUpdateTime(updateTime)
                .setNodeId(metadata.buildNodeId())
                .setValidTime(new Date(syncDeviceInfo.getValidTime()));
    }
}
