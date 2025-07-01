package com.things.cgomp.common.redis.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author things
 */
@Component
public class RedisLock {

    @Resource
    private RedissonClient redissonClient;

    /**
     * lock(), 拿不到lock线程就一直block
     */
    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    /**
     * leaseTime为加锁时间，单位为秒
     */
    public RLock lock(String lockKey, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return null;
    }

    /**
     * timeout为加锁时间，时间单位由unit确定
     */
    public RLock lock(String lockKey, TimeUnit unit, long timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    /**
     * @param lockKey   锁 key
     * @param unit      单位
     * @param waitTime  等待时间
     * @param leaseTime 锁有效时间
     * @return 加锁成功? true:成功 false: 失败
     */
    public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime) {

        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * @param lockKey   锁 key
     * @param unit      单位
     * @param waitTime  等待时间
     * @param leaseTime 锁有效时间
     * @return 加锁成功? true:成功 false: 失败
     */
    public RLock tryLockR(String lockKey, TimeUnit unit, long waitTime, long leaseTime) {

        RLock lock = redissonClient.getLock(lockKey);
        try {
            boolean success = lock.tryLock(waitTime, leaseTime, unit);
            if (success) {
                return lock;
            }
        } catch (InterruptedException e) {
            return null;
        }
        return null;
    }

    /**
     * unlock
     */
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    /**
     * unlock
     * @param lock 锁
     */
    public void unlock(RLock lock) {
        lock.unlock();
    }
}
