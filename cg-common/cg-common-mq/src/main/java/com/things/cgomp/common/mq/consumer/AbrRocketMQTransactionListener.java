package com.things.cgomp.common.mq.consumer;

import com.things.cgomp.common.mq.common.AbstractBody;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.common.QueueMsg;
import com.things.cgomp.common.mq.common.RocketMQHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

/**
 * @author things
 */
@Slf4j
public abstract class AbrRocketMQTransactionListener<Body extends AbstractBody> implements RocketMQLocalTransactionListener {

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            QueueMsg<Body> queueMsg = RocketMQHelper.deserialize((byte[]) msg.getPayload());
            if (queueMsg != null) {
                return executeLocalTransaction(queueMsg.getBody(), queueMsg.getMetadata(), arg);
            } else {
                log.warn("executeLocalTransaction deserialize fail");
                return RocketMQLocalTransactionState.ROLLBACK;
            }
        } catch (Exception e) {
            log.error("executeLocalTransaction execute error, ", e);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        QueueMsg<Body> queueMsg = RocketMQHelper.deserialize((byte[]) msg.getPayload());
        if (queueMsg != null) {
            return checkLocalTransaction(queueMsg.getBody(), queueMsg.getMetadata());
        } else {
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }

    protected abstract RocketMQLocalTransactionState executeLocalTransaction(Body msg, Metadata metadata, Object arg);

    protected abstract RocketMQLocalTransactionState checkLocalTransaction(Body msg, Metadata metadata);
}
