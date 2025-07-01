package com.things.cgomp.common.core.utils;

import java.util.concurrent.atomic.AtomicInteger;


public class CustomThreadFactory implements java.util.concurrent.ThreadFactory {

    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final String namePrefix;

    public static CustomThreadFactory forName(String name) {
        return new CustomThreadFactory(name);
    }

    private CustomThreadFactory(String name) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = name + "-";
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
