package com.things.cgomp.device.data.persistence.queue;

import com.things.cgomp.common.device.dao.td.domain.BasePersistData;
import com.things.cgomp.device.data.common.BlockingQueueWrapper;

import java.util.List;

/**
 * @author things
 */
public interface IPersistMessageService<E extends BasePersistData> {

    void initQueue(BlockingQueueWrapper<E> queue);

    void persist(E e);

    void persistMessage(String name, List<E> dataList);
}
