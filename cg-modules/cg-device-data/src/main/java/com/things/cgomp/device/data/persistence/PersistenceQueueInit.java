package com.things.cgomp.device.data.persistence;

import com.things.cgomp.common.core.utils.SpringUtils;
import com.things.cgomp.device.data.common.BlockingQueueWrapper;
import com.things.cgomp.device.data.common.PersistConsumer;
import com.things.cgomp.device.data.common.PersistQueueProperties;
import com.things.cgomp.device.data.persistence.queue.IPersistMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

/**
 * 设备数据持久化初始化类
 * @author things
 */
@Slf4j
@Component
public class PersistenceQueueInit {

    @Resource
    private PersistQueueProperties persistQueueProperties;

    @PostConstruct
    public void init() {
        Map<String, PersistConsumer> consumers = persistQueueProperties.getConsumers();
        for (Map.Entry<String, PersistConsumer> entry : consumers.entrySet()) {
            PersistConsumer consumer = entry.getValue();
            BlockingQueueWrapper<Object> tsQueue = new BlockingQueueWrapper<>(consumer.getMaxThread(), consumer.getQueueMaxNum(), consumer.getCommitBatchSize(), consumer.getMaxDelay());
            try {
                IPersistMessageService persistMessageService = SpringUtils.getBean(consumer.getPersist());
                persistMessageService.initQueue(tsQueue);
            }catch (Exception e){
                log.error("initQueue error", e);
            }
        }
    }
}
