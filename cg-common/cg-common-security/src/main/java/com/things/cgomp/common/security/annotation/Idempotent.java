package com.things.cgomp.common.security.annotation;

import com.things.cgomp.common.security.idempotent.IdempotentKeyResolver;
import com.things.cgomp.common.security.idempotent.impl.DefaultIdempotentKeyResolver;
import com.things.cgomp.common.security.idempotent.impl.TokenIdempotentKeyResolver;
import com.things.cgomp.common.security.idempotent.impl.UserIdempotentKeyResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 幂等注解
 * @author things
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

    /**
     * 超时时间，默认为 1 秒
     * 若执行时间超过此配置，请求还是会进来
     */
    int timeout() default 1;
    /**
     * 时间单位，默认为 SECONDS 秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
    /**
     * 提示信息，正在执行中的提示
     */
    String message() default "重复请求，请稍后重试";
    /**
     * 使用的 Key 解析器
     *
     * @see DefaultIdempotentKeyResolver 全局级别
     * @see UserIdempotentKeyResolver 用户级别
     * @see TokenIdempotentKeyResolver 令牌级别
     */
    Class<? extends IdempotentKeyResolver> keyResolver() default DefaultIdempotentKeyResolver.class;
    /**
     * 删除 Key，当发生异常时候
     */
    boolean deleteKeyWhenException() default true;
}
