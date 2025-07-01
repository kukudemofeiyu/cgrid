package com.things.cgomp.common.mq.producer;

import com.things.cgomp.common.mq.common.QueueMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;

/**
 * @author things
 */
public interface IProducer<M extends QueueMsg> {

    boolean syncSend(
            String topic,
            String tag,
            M message
    );

    boolean syncSend(
            String topic,
            String tag,
            byte[] message
    );

    void asyncSend(
            String topic,
            String tag,
            M message,
            SimpleServiceCallback callback
    );

    boolean sendMessageInTransaction(
            String topic,
            String tag,
            M message,
            String transactionId
    );
}
