package com.things.cgomp.common.security.aspect;

import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.utils.ServletUtils;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.security.annotation.InnerAuth;
import com.things.cgomp.common.security.enums.ErrorCodeConstants;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 内部服务调用验证处理
 * 
 * @author things
 */
@Aspect
@Component
public class InnerAuthAspect implements Ordered
{
    @Around("@annotation(innerAuth)")
    public Object innerAround(ProceedingJoinPoint point, InnerAuth innerAuth) throws Throwable
    {
        String source = ServletUtils.getRequest().getHeader(SecurityConstants.FROM_SOURCE);
        // 内部请求验证
        if (!StringUtils.equals(SecurityConstants.INNER, source))
        {
            throw exception(ErrorCodeConstants.INNER_AUTH_NOT_ALLOW);
        }

        String userid = ServletUtils.getRequest().getHeader(SecurityConstants.DETAILS_USER_ID);
        String username = ServletUtils.getRequest().getHeader(SecurityConstants.DETAILS_USERNAME);
        // 用户信息验证
        if (innerAuth.isUser() && (StringUtils.isEmpty(userid) || StringUtils.isEmpty(username)))
        {
            throw exception(ErrorCodeConstants.INNER_AUTH_NO_USER);
        }
        return point.proceed();
    }

    /**
     * 确保在权限认证aop执行前执行
     */
    @Override
    public int getOrder()
    {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
