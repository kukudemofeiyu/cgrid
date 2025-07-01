package com.things.cgomp.device.data.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobin {

    private final List<Integer> servers;  // 服务器列表（例如：["Server1", "Server2", "Server3"]）
    private final AtomicInteger currentIndex = new AtomicInteger(0);

    public RoundRobin(Integer actualNodeCount) {
        this.servers = new ArrayList<>();
        for (int i = 0; i < actualNodeCount; i++) {
            servers.add(i);
        }
    }

    // 获取下一个服务器
    public Integer getNext() {
        if (servers.isEmpty()) {
            throw new IllegalStateException("No servers available");
        }
        int index = currentIndex.getAndIncrement() % servers.size();
        return servers.get(Math.abs(index));  // 处理负数情况（确保索引有效）
    }

    public static void main(String[] args) {
        RoundRobin robinSimple = new RoundRobin(50);
        for (int i = 0; i < 100; i++) {
            Integer next = robinSimple.getNext();
            System.out.println(next);
        }
    }
}