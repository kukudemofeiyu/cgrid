package com.things.cgomp.common.security.idempotent.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.things.cgomp.common.security.annotation.Idempotent;
import com.things.cgomp.common.security.idempotent.IdempotentKeyResolver;
import com.things.cgomp.common.security.utils.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

/**
 * 用户级别的幂等 Key 解析器，使用方法名 + 方法参数 + userId + username，组装成一个 Key
 * @author things
 */
@Component
public class UserIdempotentKeyResolver implements IdempotentKeyResolver {

    @Override
    public String resolver(JoinPoint joinPoint, Idempotent idempotent) {
        String methodName = joinPoint.getSignature().toString();
        String argsStr = StrUtil.join(",", joinPoint.getArgs());
        Long userId = SecurityUtils.getUserId();
        String username = SecurityUtils.getUsername();
        return SecureUtil.md5(methodName + argsStr + userId + username);
    }
}
