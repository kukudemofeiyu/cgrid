package com.things.cgomp.app.queue.consumer;

import com.things.cgomp.app.service.ChargingPileService;
import com.things.cgomp.app.service.impl.ChargingPileServiceImpl;
import com.things.cgomp.common.mq.annotation.RocketMQConsumer;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.consumer.AbrRocketMQConsumer;
import com.things.cgomp.common.mq.message.app.AppEndChargingReqMsg;
import com.things.cgomp.order.api.enums.OrderTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@RocketMQConsumer
@RocketMQMessageListener(
        topic = MQTopics.APP,
        consumerGroup = MQTopics.Group.APP + "-" + MQTopics.Tag.APP_END_CHARGE,
        selectorExpression = MQTopics.Tag.APP_END_CHARGE
)
public class AppEndChargeConsumer extends AbrRocketMQConsumer<AppEndChargingReqMsg> {
   @Resource
   private ChargingPileService chargingPileService;
    @Override
    protected void onMessage(AppEndChargingReqMsg reqMsg, Metadata metadata) {
        log.info("AppEndChargeConsumer 收到结束充电消息, reqMsg={}, metadata={}", reqMsg, metadata);
        if (Objects.nonNull(reqMsg)){
            if (OrderTypeEnum.REAL_TIME.getType().equals(reqMsg.getOrderType())){
                chargingPileService.sendStopChargingMessage(reqMsg.getId());
            }
        }

    }
}
