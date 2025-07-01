package com.things.cgomp.common.security.idempotent.impl;

import cn.hutool.crypto.SecureUtil;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.security.annotation.Idempotent;
import com.things.cgomp.common.security.idempotent.IdempotentKeyResolver;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

/**
 * 默认（全局级别）幂等 Key 解析器，使用方法名 + 方法参数，组装成一个 Key
 * @author things
 */
@Component
public class DefaultIdempotentKeyResolver implements IdempotentKeyResolver {

    @Override
    public String resolver(JoinPoint joinPoint, Idempotent idempotent) {
        String methodName = joinPoint.getSignature().toString();
        String argsStr = StringUtils.join(",", joinPoint.getArgs());
        return SecureUtil.md5(methodName + argsStr);
    }
}
