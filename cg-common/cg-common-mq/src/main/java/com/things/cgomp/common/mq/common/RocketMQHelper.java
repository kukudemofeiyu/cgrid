package com.things.cgomp.common.mq.common;

import com.things.cgomp.common.mq.common.codec.ProtostuffCodec;
import lombok.Getter;
import lombok.SneakyThrows;

/**
 * @author things
 */
public class RocketMQHelper {

    @Getter
    private static final ProtostuffCodec codec;
    @Getter
    private static final Class<QueueMsg> queueMsgClass;

    static {
        codec = new ProtostuffCodec();
        queueMsgClass = initQueueMsg();
    }

    @SneakyThrows
    private static Class<QueueMsg> initQueueMsg() {
        return (Class<QueueMsg>) Class.forName(QueueMsg.class.getName());
    }

    public static QueueMsg deserialize(byte[] payload) {
        return codec.deserialize(queueMsgClass, payload);
    }
}
