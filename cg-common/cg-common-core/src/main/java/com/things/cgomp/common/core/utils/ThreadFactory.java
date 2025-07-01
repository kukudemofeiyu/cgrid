package com.things.cgomp.common.core.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author things
 */
public class ThreadFactory implements java.util.concurrent.ThreadFactory {

    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final String namePrefix;

    public static ThreadFactory forName(String name) {
        return new ThreadFactory(name);
    }

    private ThreadFactory(String name) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = name + "-" +
                poolNumber.getAndIncrement() +
                "-th-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}
