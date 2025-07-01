package com.things.cgomp.devicescale.message;

import java.util.LinkedHashMap;
import java.util.Map;


public class LRUMap<K, V> extends LinkedHashMap<K, V> {

    private int maxSize;

    public LRUMap(int maxSize) {
        super(maxSize, 0.75F, true);
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return (size() > this.maxSize);
    }

}