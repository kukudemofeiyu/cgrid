package com.things.cgomp.common.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 *
 * @author things
 **/
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisService {
    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Long timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public <T> Boolean setCacheObjectIfAbsent(final String key, final T value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public <T> Boolean setCacheObjectIfAbsent(final String key, final T value, final Long timeout, final TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间
     */
    public long getExpire(final String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public boolean deleteObject(final Collection collection) {
        return redisTemplate.delete(collection) > 0;
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 删除Hash中的某条数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return 是否成功
     */
    public boolean deleteCacheMapValue(final String key, final String hKey) {
        return redisTemplate.opsForHash().delete(key, hKey) > 0;
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 从阻塞队列左边弹出数据
     */
    public <T> T leftPop(String key, long timeout, TimeUnit unit) {
        ListOperations<String, T> opsForList = (ListOperations<String, T>) redisTemplate.opsForList();
        return opsForList.leftPop(key, timeout, unit);
    }

    /**
     * 从阻塞队列左边写数据
     */
    public <T> void leftPush(String key, T t) {
        ListOperations<String, T> opsForList = (ListOperations<String, T>) redisTemplate.opsForList();
        opsForList.leftPush(key, t);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public  <T> boolean hset(String key, String item, T value, long time) {
        try {

            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public <T> T hget(String key, String item) {
        return (T) redisTemplate.opsForHash().get(key, item);
    }

    public <T> List<T> hMGet(String key, List<String> hashKeys){
        return redisTemplate.opsForHash().multiGet(key, hashKeys);
    }

    public <T> Map<String, T> hAllGet(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    public Long hSize(String key) {
        try {

            return redisTemplate.opsForHash().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void leftPush(String key, String object, Long timeout) {
        try {
            redisTemplate.executePipelined((RedisConnection connection) -> {
                connection.lPush(key.getBytes(), object.getBytes());
                connection.pExpire(key.getBytes(), timeout);
                return null;
            }, null);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public <T> void mSet(Map<String, T> dataMap) {
        redisTemplate.opsForValue().multiSet(dataMap);
    }

    public <T> void mSet(Map<String, T> dataMap, final long timeout, final TimeUnit unit) {
        redisTemplate.opsForValue().multiSet(dataMap);
        dataMap.keySet().forEach(key -> redisTemplate.expire(key, timeout, unit));
    }

    public <T> List<T> mGet(List<String> keys){
        try {
            return redisTemplate.opsForValue().multiGet(keys);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void hMSetForMap(Map<String, Map<String, Object>> map) {
        redisTemplate.executePipelined((RedisCallback<String>) redisConnection -> {
            try {
                Map<byte[], byte[]> byteMap = null;
                for (String key :
                        map.keySet()) {
                    Map<String, Object> value = map.get(key);
                    byteMap = new HashMap<>();
                    for (Map.Entry<String, Object> entry :
                            value.entrySet()) {
                        byteMap.put(entry.getKey().getBytes(), String.valueOf(entry.getValue()).getBytes());
                    }
                    redisConnection.hMSet(key.getBytes(), byteMap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });

    }

    public Map<String, Object> hGetForRT(String rtKey, List<String> keys) {
        Map<String, Object> result = new HashMap<>();
        try {
            byte[][] rawHashKeys = new byte[keys.size()][];
            int counter = 0;
            for (String hashKey : keys) {
                rawHashKeys[counter++] = hashKey.getBytes();
            }
            List<byte[]> rawValues = (List<byte[]>) redisTemplate.execute(connection ->
                    connection.hMGet(rtKey.getBytes(), rawHashKeys), true);

            if (rawValues == null || rawValues.isEmpty()) {
                return result;
            }
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                byte[] value = rawValues.get(i);
                if (value == null) {
                    continue;
                }
                String valueStr = new String(value);
                if (valueStr.equals("null")) {
                    continue;
                }
                result.put(key, valueStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

}
