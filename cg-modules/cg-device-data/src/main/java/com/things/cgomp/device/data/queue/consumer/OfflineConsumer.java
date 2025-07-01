package com.things.cgomp.device.data.queue.consumer;

import com.things.cgomp.common.device.constants.RedisKeyConstant;
import com.things.cgomp.common.device.dao.device.domain.DeviceConnectStatus;
import com.things.cgomp.common.device.dao.device.mapper.DeviceConnectStatusMapper;
import com.things.cgomp.common.device.enums.DeviceNetWorkState;
import com.things.cgomp.common.mq.annotation.RocketMQConsumer;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.consumer.AbrRocketMQConsumer;
import com.things.cgomp.common.mq.message.DeviceOffLineReqMsg;
import com.things.cgomp.common.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * 设备离线消息消费者
 *
 * @author things
 */
@Slf4j
@RocketMQConsumer
@RocketMQMessageListener(
        topic = MQTopics.STATUS,
        consumerGroup = MQTopics.Group.STATUS+ "-" + MQTopics.Tag.OFFLINE,
        selectorExpression = MQTopics.Tag.OFFLINE
)
public class OfflineConsumer extends AbrRocketMQConsumer<DeviceOffLineReqMsg> {

    @Resource
    private RedisService redisService;
    @Resource
    private DeviceConnectStatusMapper deviceConnectStatusMapper;

    @Override
    protected void onMessage(DeviceOffLineReqMsg reqMsg, Metadata metadata) {
        log.info("收到设备离线消息, metadata={}, body={}", metadata, reqMsg);

        // 将滞后120s的下线消息丢弃
        long currentTimeMillis = System.currentTimeMillis();
        if ((Math.abs(metadata.getEventTime() - currentTimeMillis)) / 1000 > 120) {
            return;
        }

        DeviceConnectStatus connectStatus = deviceConnectStatusMapper.selectByDeviceId(metadata.getDeviceId());
        // 防止多次下线
        if (connectStatus != null &&
                (DeviceNetWorkState.offLine.getValue().equals(connectStatus.getStatus())
                        || !connectStatus.getNodeId().equals(metadata.buildNodeId()))) {
            return;
        }
        // 针对设备下线之后再马上上线情况。有可能会先处理上线消息，再处理下线消息
        if (connectStatus != null && DeviceNetWorkState.onLine.getValue().equals(connectStatus.getStatus())) {
            if ((connectStatus.getUpdateTime().getTime() - metadata.getEventTime()) > 0) {
                return;
            }
        }
        DeviceConnectStatus offlineStatus = buildOfflineStatus(metadata);
        // 设备网关是否正在重启, 重启时不离线
        Boolean isGatewayRestart = isDeviceGatewayRestart(offlineStatus);
        if (isGatewayRestart) {
            return;
        }
        // 更新离线状态
        deviceConnectStatusMapper.updateById(offlineStatus);
    }

    private DeviceConnectStatus buildOfflineStatus(Metadata metadata){
        return new DeviceConnectStatus()
                .setStatus(DeviceNetWorkState.offLine.getValue())
                .setDeviceId(metadata.getDeviceId())
                .setUpdateTime(new Timestamp(metadata.getEventTime()));
    }

    /**
     * 设备网关是否正在重启
     */
    private Boolean isDeviceGatewayRestart(DeviceConnectStatus offlineConnectStatus) {
        String nodeId = offlineConnectStatus.getNodeId();
        String key = RedisKeyConstant.DEVICE_GATEWAY_RESTART_PREFIX + nodeId;
        return redisService.hasKey(key);
    }
}
