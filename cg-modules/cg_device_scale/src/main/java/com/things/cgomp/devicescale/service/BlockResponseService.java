package com.things.cgomp.devicescale.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.things.cgomp.devicescale.message.AbstractBody;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
@Service
@Slf4j
public class BlockResponseService {
    private Map<String, BlockingQueue<AbstractBody>> data = new ConcurrentHashMap<>();

    public AbstractBody getResponseData(String key) {
        BlockingQueue<AbstractBody> blockingQueue = data.get(key);
        if (null == blockingQueue) {
            blockingQueue = new ArrayBlockingQueue<>(1);
            data.put(key, blockingQueue);
        }
        try {
            return blockingQueue.poll(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return null;
        }
    }

    public void putData(String key, AbstractBody rspData) {
        BlockingQueue<AbstractBody> blockingQueue = data.get(key);
        if (null == blockingQueue) {
            log.warn("没有对应的数据消费者,data={}", rspData);
            return;
        }
        try {
            blockingQueue.put(rspData);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
