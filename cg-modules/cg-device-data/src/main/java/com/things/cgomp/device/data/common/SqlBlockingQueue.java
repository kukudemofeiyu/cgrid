package com.things.cgomp.device.data.common;

import com.things.cgomp.common.core.utils.ThreadFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author things
 */
@Slf4j
@Data
public class SqlBlockingQueue<E> {
    private final int maxThreads;
    // 单次最大提交数量
    private final int batchSize;
    // 消息获取超时时间
    private final long maxDelay;

    private ExecutorService executor;
    private final BlockingQueue<E> queue;

    //是否排序
    boolean sortEnabled = true;


    public void init(BiConsumer<String, List<E>> saveFunction, Comparator<E> batchUpdateComparator, int index) {
        String name = "ts-consumer-" + index;
        executor = Executors.newSingleThreadExecutor(ThreadFactory.forName(name));
        executor.submit(() -> {
            List<E> entities = new ArrayList<>(batchSize);
            //   long lastSaveTime = 0;
            while (!Thread.interrupted()) {
                try {
                    // long currentTs = System.currentTimeMillis();
                    // E attr = queue.take();
                    E attr = queue.poll(maxDelay, TimeUnit.MILLISECONDS);
                    if (attr == null) {
                        continue;
                    } else {
                        entities.add(attr);
                    }
                    queue.drainTo(entities, batchSize - 1);
                    Stream<E> entitiesStream = entities.stream();
                    saveFunction.accept(name,
                            (sortEnabled && batchUpdateComparator != null
                                    ? entitiesStream.sorted(batchUpdateComparator) : entitiesStream
                            )
                                    .collect(Collectors.toList())
                    );

                    entities.clear();
                } catch (Exception e) {
                    log.error("TsSqlBlockingQueue.Queue polling was interrupted,name={}", name, e);
                    entities.clear();
                }
            }


        });

    }

    private boolean outMaxDelayTime(long currentTs, long lastSaveTime, long currentTimeMillis) {
        return (0 > maxDelay) || (currentTimeMillis - lastSaveTime > maxDelay);

    }

    public void add(E e) {
        try {
            queue.put(e);
        } catch (InterruptedException interruptedException) {
            log.error("TsSqlBlockingQueue.Queue add error,e)={}", e, interruptedException);
        }
    }


}
