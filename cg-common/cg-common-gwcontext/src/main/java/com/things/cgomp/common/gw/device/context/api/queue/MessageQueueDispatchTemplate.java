package com.things.cgomp.common.gw.device.context.api.queue;

import com.things.cgomp.common.mq.message.callback.CallBackMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import com.things.cgomp.common.mq.common.QueueMsg;
import com.things.cgomp.common.mq.producer.IProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageQueueDispatchTemplate implements MessageRequestTemplate< SimpleServiceCallback<CallBackMsg>> {
    @Autowired(required = false)
    private IProducer producer;


    @Override
    public boolean send(String topic, String tag, QueueMsg message) {
        return producer.syncSend(topic, topic, message);
    }

    @Override
    public void asyncSend(String topic, String tag, QueueMsg message, SimpleServiceCallback<CallBackMsg> callback) {
        producer.asyncSend(topic, tag, message, callback);
    }

    @Override
    public boolean sendTransactionMsg(String topic, String tag, QueueMsg message, String transactionId) {
        return producer.sendMessageInTransaction(topic, tag, message,transactionId);
    }
}
