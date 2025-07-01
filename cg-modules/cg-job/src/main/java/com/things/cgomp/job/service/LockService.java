package com.things.cgomp.job.service;

import com.things.cgomp.common.redis.service.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author janson
 */
@Slf4j
@Component
public class LockService {

    @Resource
    private RedisLock redisLock;

    public RLock getLock(String lockKey){
       return getLock(lockKey, TimeUnit.SECONDS, 5, 10);
    }

    public RLock getLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime){
        RLock lock = redisLock.tryLockR(lockKey, unit, waitTime, leaseTime);
        if (lock == null) {
            // 保证上一个任务未处理完成，本次任务不执行
            log.warn("上一次任务还未执行完成，本次任务跳过");
            return null;
        }
        return lock;
    }
}
