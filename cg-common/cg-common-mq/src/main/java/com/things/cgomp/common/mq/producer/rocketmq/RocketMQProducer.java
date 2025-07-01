package com.things.cgomp.common.mq.producer.rocketmq;

import com.things.cgomp.common.mq.common.QueueMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import com.things.cgomp.common.mq.producer.IProducer;
import com.things.cgomp.common.mq.common.RocketMQHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author things
 */
@Slf4j
@Component
public class RocketMQProducer<M extends QueueMsg> implements IProducer<M> {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public boolean syncSend(String topic, String tag, M message) {
        if (!StringUtils.hasLength(topic)) {
            log.error("topic不能为空");
            return false;
        }
        byte[] payload = encode(message);
        return syncSend(topic, tag, payload);
    }

    @Override
    public boolean syncSend(String topic, String tag, byte[] message) {
        if (!StringUtils.hasLength(topic)) {
            log.error("topic不能为空");
            return false;
        }
        Message<byte[]> sendMsg = MessageBuilder
                .withPayload(message)
                .build();
        String destination = buildDestination(topic, tag);
        SendResult result = rocketMQTemplate.syncSend(destination, sendMsg);
        log.info("消息发送成功：{}，消息内容:{}", result.getSendStatus(), message);
        return true;
    }

    @Override
    public void asyncSend(String topic, String tag, M message, SimpleServiceCallback callback) {
        if (!StringUtils.hasLength(topic)) {
            callback.onError(new Exception("Message sending failed, topic is empty"));
            return;
        }
        Message<byte[]> sendMsg = MessageBuilder
                .withPayload(encode(message))
                .build();
        String destination = buildDestination(topic, tag);

        rocketMQTemplate.asyncSend(
                destination,
                sendMsg,
                new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        log.debug("消息发送成功,topic={},tag={},message={}", topic, tag, message);
                        if(callback!=null){
                            callback.onSuccess(null);
                        }

                    }

                    @Override
                    public void onException(Throwable throwable) {
                        log.error("消息投递失败,topic={},tag={}, message={},", topic, tag, message, throwable);
                        if(callback!=null){
                            callback.onError(throwable);
                        }
                    }
                }
        );
    }

    @Override
    public boolean sendMessageInTransaction(String topic, String tag, M message, String transactionId) {
        if (!StringUtils.hasLength(topic)) {
            log.error("topic不能为空");
            return false;
        }
        Message<byte[]> sendMsg = MessageBuilder
                .withPayload(encode(message))
                .build();
        String destination = buildDestination(topic, tag);
        TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction(destination, sendMsg, transactionId);

        log.info("事务消息发送结果：{}， 本地事务状态：{}，消息内容:{}", result.getSendStatus(), result.getLocalTransactionState(), message);
        if (!SendStatus.SEND_OK.equals(result.getSendStatus())) {
            return false;
        }
        return LocalTransactionState.COMMIT_MESSAGE.equals(result.getLocalTransactionState());
    }

    private String buildDestination(String topic, String tag) {
        if (StringUtils.hasLength(tag)) {
            return topic + ":" + tag;
        }
        return topic;
    }

    private byte[] encode(Object msg) {
        return RocketMQHelper.getCodec().serialize(msg);
    }
}
