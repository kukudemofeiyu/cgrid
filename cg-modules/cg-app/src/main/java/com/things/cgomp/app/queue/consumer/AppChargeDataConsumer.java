package com.things.cgomp.app.queue.consumer;

import cn.hutool.json.JSONUtil;
import com.things.cgomp.app.domain.dto.ChargingRealDataDTO;
import com.things.cgomp.app.handler.WebSocketHandler;
import com.things.cgomp.app.queue.PortSessionManager;
import com.things.cgomp.common.core.utils.bean.BeanUtils;
import com.things.cgomp.common.mq.annotation.RocketMQConsumer;
import com.things.cgomp.common.mq.common.AbstractBody;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.common.QueueMsg;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.consumer.AbrRocketMQConsumer;
import com.things.cgomp.common.mq.message.DeviceChargeDataMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import com.things.cgomp.common.mq.producer.IProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;

/**
 * 充电数据消费
 *
 * @author things
 */
@Slf4j
@RocketMQConsumer
@RocketMQMessageListener(
        topic = MQTopics.APP,
        consumerGroup = MQTopics.Group.APP + "-" + MQTopics.Tag.CHARGE_INFO,
        selectorExpression = MQTopics.Tag.CHARGE_INFO
)
public class AppChargeDataConsumer extends AbrRocketMQConsumer<DeviceChargeDataMsg> {
    @Override
    protected void onMessage(DeviceChargeDataMsg reqMsg, Metadata metadata) {
        log.info("AppChargeDataConsumer 收到充电数据消息, reqMsg={}, metadata={}", reqMsg, metadata);
        Long portId = reqMsg.getPortId();
        //根据端口ID通知用户
        WebSocketSession webSocketSession = PortSessionManager.getWebSocketSession(portId);
        if (webSocketSession != null) {
            WebSocketHandler.sendMessage(webSocketSession, JSONUtil.toJsonStr(reqMsg));
        }
    }
}
