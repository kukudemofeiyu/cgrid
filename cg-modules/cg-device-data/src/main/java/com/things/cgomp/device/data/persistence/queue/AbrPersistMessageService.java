package com.things.cgomp.device.data.persistence.queue;

import com.things.cgomp.common.device.dao.td.domain.BasePersistData;
import com.things.cgomp.device.data.common.BlockingQueueWrapper;
import com.things.cgomp.device.data.common.RoundRobin;

/**
 * @author things
 */
public abstract class AbrPersistMessageService<E extends BasePersistData> implements IPersistMessageService<E> {

    protected BlockingQueueWrapper<E> tsQueue;
    protected RoundRobin roundRobin;

    @Override
    public void initQueue(BlockingQueueWrapper<E> queue) {
        this.tsQueue = queue;
        tsQueue.init(this::persistMessage, null);
        roundRobin = new RoundRobin(queue.getMaxThreads());
    }

    @Override
    public void persist(E e) {
        Integer index = roundRobin.getNext();
        tsQueue.add(e, index);
    }
}
