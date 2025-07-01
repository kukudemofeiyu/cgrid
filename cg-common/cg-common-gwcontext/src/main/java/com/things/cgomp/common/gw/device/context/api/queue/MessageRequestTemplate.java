package com.things.cgomp.common.gw.device.context.api.queue;


import com.things.cgomp.common.mq.common.QueueMsg;
import com.things.cgomp.common.mq.message.callback.CallBackMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import com.things.cgomp.common.mq.common.AbstractBody;

public interface MessageRequestTemplate< CallBack extends SimpleServiceCallback<CallBackMsg>> {
    boolean send(String topic, String tag, QueueMsg message);

    void asyncSend(String topic, String tag, QueueMsg message, CallBack callback);

    boolean sendTransactionMsg(
            String topic,
            String tag,
            QueueMsg message,
            String transactionId
    );
}
