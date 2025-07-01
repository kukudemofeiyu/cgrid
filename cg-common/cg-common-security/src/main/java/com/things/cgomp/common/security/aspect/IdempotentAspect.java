package com.things.cgomp.common.security.aspect;

import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.core.exception.enums.GlobalErrorCodeConstants;
import com.things.cgomp.common.core.utils.CollectionUtils;
import com.things.cgomp.common.redis.service.RedisService;
import com.things.cgomp.common.security.annotation.Idempotent;
import com.things.cgomp.common.security.idempotent.IdempotentKeyResolver;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 拦截声明了 {@link Idempotent} 注解的方法，实现幂等操作
 * @author things
 */
@Slf4j
@Aspect
@Component
public class IdempotentAspect {

    /**
     * IdempotentKeyResolver 集合
     */
    private final Map<Class<? extends IdempotentKeyResolver>, IdempotentKeyResolver> keyResolvers;

    private final RedisService redisService;

    public IdempotentAspect(List<IdempotentKeyResolver> keyResolvers, RedisService redisService) {
        this.keyResolvers = CollectionUtils.convertMap(keyResolvers, IdempotentKeyResolver::getClass);
        this.redisService = redisService;
    }

    @Around(value = "@annotation(idempotent)")
    public Object aroundPointCut(ProceedingJoinPoint joinPoint, Idempotent idempotent) throws Throwable {
        // 获得 IdempotentKeyResolver
        IdempotentKeyResolver keyResolver = keyResolvers.get(idempotent.keyResolver());
        Assert.notNull(keyResolver, "找不到对应的 IdempotentKeyResolver");
        // 解析 Key
        String key = keyResolver.resolver(joinPoint, idempotent);

        // 1. 锁定 Key
        Boolean success = lockKey(key, idempotent.timeout(), idempotent.timeUnit());
        // 锁定失败，抛出异常
        if (!success) {
            log.info("[IdempotentAspect.aroundPointCut][方法({}) 参数({}) 存在重复请求]", joinPoint.getSignature().toString(), joinPoint.getArgs());
            throw new ServiceException(GlobalErrorCodeConstants.REPEATED_REQUESTS.getCode(), idempotent.message());
        }

        // 2. 执行逻辑
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            // 3. 异常时，删除 Key
            if (idempotent.deleteKeyWhenException()) {
                deleteKey(key);
            }
            throw throwable;
        }
    }

    private Boolean lockKey(String key, long timeout, TimeUnit timeUnit){
        String redisKey = formatKey(key);
        return redisService.setCacheObjectIfAbsent(redisKey, "", timeout, timeUnit);
    }

    private void deleteKey(String key){
        String redisKey = formatKey(key);
        redisService.deleteObject(redisKey);
    }

    /**
     * 幂等操作redisKey
     * KEY 格式：idempotent:%s
     * VALUE 格式：String
     * 过期时间：不固定
     */
    private static final String IDEMPOTENT = "idempotent:%s";

    private static String formatKey(String key) {
        return String.format(IDEMPOTENT, key);
    }
}
