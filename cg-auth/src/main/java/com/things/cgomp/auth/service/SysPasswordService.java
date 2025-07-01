package com.things.cgomp.auth.service;

import com.things.cgomp.auth.enums.ErrorCodeConstants;
import com.things.cgomp.common.core.constant.CacheConstants;
import com.things.cgomp.common.core.constant.Constants;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.redis.service.RedisService;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 登录密码方法
 * 
 * @author things
 */
@Component
public class SysPasswordService
{
    @Autowired
    private RedisService redisService;

    private int maxRetryCount = CacheConstants.PASSWORD_MAX_RETRY_COUNT;

    private Long lockTime = CacheConstants.PASSWORD_LOCK_TIME;

    @Autowired
    private SysRecordLogService recordLogService;

    /**
     * 登录账户密码错误次数缓存键名
     * 
     * @param username 用户名
     * @return 缓存键key
     */
    private String getCacheKey(String username)
    {
        return CacheConstants.PWD_ERR_CNT_KEY + username;
    }

    public void validate(SysUser user, String password)
    {
        String username = user.getUsername();

        Integer retryCount = redisService.getCacheObject(getCacheKey(username));

        if (retryCount == null)
        {
            retryCount = 0;
        }

        if (retryCount >= maxRetryCount)
        {
            ServiceException exception = exception(ErrorCodeConstants.USER_LOGIN_LOCK, maxRetryCount, lockTime);
            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, exception.getMessage());
            throw exception;
        }

        if (!matches(user, password))
        {
            retryCount = retryCount + 1;
            ServiceException exception = exception(ErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS);
            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, exception.getMessage());
            redisService.setCacheObject(getCacheKey(username), retryCount, lockTime, TimeUnit.MINUTES);
            throw exception;
        }
        else
        {
            clearLoginRecordCache(username);
        }
    }

    public boolean matches(SysUser user, String rawPassword)
    {
        return SecurityUtils.matchesPassword(rawPassword, user.getPassword());
    }

    public void clearLoginRecordCache(String loginName)
    {
        if (redisService.hasKey(getCacheKey(loginName)))
        {
            redisService.deleteObject(getCacheKey(loginName));
        }
    }
}
