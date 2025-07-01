package com.things.cgomp.common.security.idempotent;

import com.things.cgomp.common.security.annotation.Idempotent;
import org.aspectj.lang.JoinPoint;

/**
 * 幂等 Key 解析器接口
 * @author things
 */
public interface IdempotentKeyResolver {

    /**
     * 解析 Key
     *
     * @param idempotent 幂等注解
     * @param joinPoint  AOP 切面
     * @return Key
     */
    String resolver(JoinPoint joinPoint, Idempotent idempotent);
}
