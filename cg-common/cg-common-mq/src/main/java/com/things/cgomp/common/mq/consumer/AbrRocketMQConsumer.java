package com.things.cgomp.common.mq.consumer;

import com.things.cgomp.common.mq.common.AbstractBody;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.common.QueueMsg;
import com.things.cgomp.common.mq.common.RocketMQHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 * @author things
 */
@Slf4j
public abstract class AbrRocketMQConsumer<Body extends AbstractBody> implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            QueueMsg<Body> queueMsg = RocketMQHelper.deserialize(messageExt.getBody());
            if (queueMsg != null) {
                onMessage(queueMsg.getBody(), queueMsg.getMetadata());
            }
        }catch (Exception e){
            log.error("RocketMQListener onMessage error, ", e);
            throw e;
        }
    }

    protected abstract void onMessage(Body reqMsg, Metadata metadata);
}
