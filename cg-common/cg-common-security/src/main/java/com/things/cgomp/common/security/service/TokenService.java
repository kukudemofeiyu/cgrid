package com.things.cgomp.common.security.service;

import com.things.cgomp.common.core.constant.CacheConstants;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.utils.JwtUtils;
import com.things.cgomp.common.core.utils.ServletUtils;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.core.utils.ip.IpUtils;
import com.things.cgomp.common.core.utils.uuid.IdUtils;
import com.things.cgomp.common.redis.service.RedisService;
import com.things.cgomp.common.security.bean.TokenResponse;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.system.api.domain.SysRole;
import com.things.cgomp.system.api.domain.SysUser;
import com.things.cgomp.system.api.model.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 * 
 * @author things
 */
@Component
public class TokenService
{
    private static final Logger log = LoggerFactory.getLogger(TokenService.class);

    @Autowired
    private RedisService redisService;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private final static long expireTime = CacheConstants.EXPIRATION;

    private final static String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;

    private final static Long MILLIS_MINUTE_TEN = CacheConstants.REFRESH_TIME * MILLIS_MINUTE;

    /**
     * 创建令牌
     */
    public TokenResponse createAppToken(LoginUser loginUser)
    {
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        loginUser.setIpaddr(IpUtils.getIpAddr());
        refreshToken(loginUser);

        // Jwt存储信息
        Map<String, Object> claimsMap = new HashMap<String, Object>();
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, loginUser.getUserid());
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, loginUser.getUsername());

        // 接口返回信息
        return new TokenResponse()
                .setAccess_token(JwtUtils.createToken(claimsMap))
                .setExpires_in(expireTime);
    }

    public TokenResponse createToken(LoginUser loginUser)
    {
        String token = IdUtils.fastUUID();
        Long userId = loginUser.getSysUser().getUserId();
        String userName = loginUser.getSysUser().getUsername();
        loginUser.setToken(token);
        loginUser.setUserid(userId);
        loginUser.setUsername(userName);
        loginUser.setIpaddr(IpUtils.getIpAddr());
        refreshToken(loginUser);

        // Jwt存储信息
        Map<String, Object> claimsMap = new HashMap<String, Object>();
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, userId);
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, userName);
        claimsMap.put(SecurityConstants.DETAILS_OPERATOR_ID, loginUser.getOperatorId());

        // 接口返回信息
        return new TokenResponse()
                .setAccess_token(JwtUtils.createToken(claimsMap))
                .setExpires_in(expireTime);
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser()
    {
        return getLoginUser(ServletUtils.getRequest());
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request)
    {
        // 获取请求携带的令牌
        String token = SecurityUtils.getToken(request);
        return getLoginUser(token);
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(String token)
    {
        LoginUser user = null;
        try
        {
            if (StringUtils.isNotEmpty(token))
            {
                String userkey = JwtUtils.getUserKey(token);
                user = redisService.getCacheObject(getTokenKey(userkey));
                return user;
            }
        }
        catch (Exception e)
        {
            log.error("获取用户信息异常'{}'", e.getMessage());
        }
        return user;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser)
    {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken()))
        {
            refreshToken(loginUser);
        }
    }

    public void removeToken(
            Long userId
    ) {
        Collection<String> keys = redisService.keys(CacheConstants.LOGIN_TOKEN_KEY + "*");
        if(CollectionUtils.isEmpty(keys)){
            return;
        }

        keys.forEach(key -> removeToken(
                userId,
                key
        ));
    }

    private void removeToken(
            Long userId,
            String key
    ) {
        LoginUser user = redisService.getCacheObject(key);
        if (user == null) {
            return;
        }

        if(!userId.equals(user.getUserid())){
            return;
        }

        redisService.deleteObject(key);
    }

    /**
     * 删除用户缓存信息
     */
    public void delLoginUser(String token)
    {
        if (StringUtils.isNotEmpty(token))
        {
            String userkey = JwtUtils.getUserKey(token);
            redisService.deleteObject(getTokenKey(userkey));
        }
    }

    /**
     * 验证令牌有效期，相差不足120分钟，自动刷新缓存
     *
     * @param loginUser
     */
    public void verifyToken(LoginUser loginUser)
    {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN)
        {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser)
    {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisService.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }

    private String getTokenKey(String token)
    {
        return ACCESS_TOKEN + token;

    }

    public void updateUserSession(SysUser user) {
        Collection<String> keys = redisService.keys(CacheConstants.LOGIN_TOKEN_KEY + "*");

        keys.stream()
                .map(key -> redisService.<LoginUser>getCacheObject(key))
                .filter(Objects::nonNull)
                .filter(loginUser -> user.getUserId().equals(loginUser.getUserid()))
                .forEach(loginUser -> updateUserSession(
                                user,
                                loginUser
                        )
                );
    }

    public void updateUserSession(SysRole role) {
        Collection<String> keys = redisService.keys(CacheConstants.LOGIN_TOKEN_KEY + "*");

        keys.stream()
                .map(key -> redisService.<LoginUser>getCacheObject(key))
                .filter(Objects::nonNull)
                .filter(loginUser -> role.getRoleId().equals(loginUser.buildRoleId()))
                .forEach(loginUser -> updateUserSession(
                                role,
                                loginUser
                        )
                );
    }

    private void updateUserSession(
            SysUser user,
            LoginUser loginUser
    ) {
        SysUser sysUser = loginUser.getSysUser();
        if (sysUser != null) {
            sysUser.setSiteRange(user.getSiteRange());
            sysUser.setOperatorRange(user.getOperatorRange());
        }
        refreshToken(loginUser);
    }

    private void updateUserSession(
            SysRole role,
            LoginUser loginUser
    ) {
        refreshToken(loginUser);
    }
}