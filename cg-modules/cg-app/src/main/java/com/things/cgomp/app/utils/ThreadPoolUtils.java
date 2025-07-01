package com.things.cgomp.app.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.*;

@Component
public class ThreadPoolUtils {

    private static ExecutorService threadPool;

    @Value("${thread.pool.coreSize:10}")
    private int corePoolSize;

    @Value("${thread.pool.maxSize:20}")
    private int maxPoolSize;

    @Value("${thread.pool.queueCapacity:100}")
    private int queueCapacity;

    @Value("${thread.pool.keepAliveTime:60}")
    private long keepAliveTime;

    /**
     * 获取线程池（单例模式）
     */
    public synchronized ExecutorService getThreadPool() {
        if (threadPool == null) {
            threadPool = new ThreadPoolExecutor(
                    corePoolSize,
                    maxPoolSize,
                    keepAliveTime,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(queueCapacity),
                    Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.AbortPolicy()
            );
        }
        return threadPool;
    }

    /**
     * 关闭线程池
     */
    @PreDestroy
    public void shutdown() {
        if (threadPool != null) {
            threadPool.shutdown();
            try {
                if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                    threadPool.shutdownNow();
                }
            } catch (InterruptedException e) {
                threadPool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 监控线程池状态
     */
    public String monitorThreadPool() {
        if (threadPool instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor executor = (ThreadPoolExecutor) threadPool;
            return String.format(
                    "ThreadPool Status: [PoolSize: %d, ActiveThreads: %d, QueueSize: %d, CompletedTasks: %d]",
                    executor.getPoolSize(),
                    executor.getActiveCount(),
                    executor.getQueue().size(),
                    executor.getCompletedTaskCount()
            );
        }
        return "ThreadPool is not initialized or not a ThreadPoolExecutor.";
    }
}

