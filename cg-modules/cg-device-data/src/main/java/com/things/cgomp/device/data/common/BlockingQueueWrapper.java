package com.things.cgomp.device.data.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiConsumer;

/**
 * @author things
 */
@Slf4j
@Data
public class BlockingQueueWrapper<E> {
    /**
     * 最大线程数
     */
    private final int maxThreads;
    /**
     * 最大容量
     */
    private final int tsQueMaxNum;
    /**
     * 单次最大提交数量
     */
    private final int batchSize;
    /**
     * 消息获取超时时间
     */
    private final long maxDelay;

    private final CopyOnWriteArrayList<SqlBlockingQueue<E>> queues = new CopyOnWriteArrayList<>();

    public void init(BiConsumer<String, List<E>> saveFunction, Comparator<E> batchUpdateComparator) {
        for (int i = 0; i < maxThreads; i++) {
            SqlBlockingQueue<E> queue = new SqlBlockingQueue<>(maxThreads, batchSize, maxDelay, new LinkedBlockingQueue<>(tsQueMaxNum));
            queues.add(queue);
            queue.init(saveFunction, batchUpdateComparator, i);
        }
    }

    public void add(E e,int queueIndex) {
        queues.get(queueIndex).add(e);
    }


}
