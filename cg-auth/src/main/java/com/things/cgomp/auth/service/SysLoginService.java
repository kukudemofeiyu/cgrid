package com.things.cgomp.auth.service;

import com.things.cgomp.auth.enums.ErrorCodeConstants;
import com.things.cgomp.common.core.constant.CacheConstants;
import com.things.cgomp.common.core.constant.Constants;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.constant.UserConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.enums.CommonStatus;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.core.exception.enums.GlobalErrorCodeConstants;
import com.things.cgomp.common.core.text.Convert;
import com.things.cgomp.common.core.utils.DateUtils;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.core.utils.ip.IpUtils;
import com.things.cgomp.common.redis.service.RedisService;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.system.api.RemoteOperatorService;
import com.things.cgomp.system.api.RemoteUserService;
import com.things.cgomp.system.api.domain.SysOperator;
import com.things.cgomp.system.api.domain.SysUser;
import com.things.cgomp.system.api.model.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception0;

/**
 * 登录校验方法
 * 
 * @author things
 */
@Slf4j
@Component
public class SysLoginService
{
    @Resource
    private RemoteUserService remoteUserService;

    @Resource
    private RemoteOperatorService remoteOperatorService;

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private SysRecordLogService recordLogService;

    @Resource
    private RedisService redisService;

    /**
     * 登录
     */
    public LoginUser login(String username, String password)
    {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password))
        {
            throwException(username, exception(ErrorCodeConstants.AUTH_LOGIN_USERNAME_PASSWORD_NEED));
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            throwException(username, exception(ErrorCodeConstants.AUTH_LOGIN_PASSWORD_NOT_IN_RANGE));
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            throwException(username, exception(ErrorCodeConstants.AUTH_LOGIN_USERNAME_NOT_IN_RANGE));
        }
        // IP黑名单校验
        String blackStr = Convert.toStr(redisService.getCacheObject(CacheConstants.SYS_LOGIN_BLACKIPLIST));
        if (IpUtils.isMatchedIp(blackStr, IpUtils.getIpAddr()))
        {
            throwException(username, exception(ErrorCodeConstants.AUTH_LOGIN_IP_IN_BLACKLIST));
        }
        // 查询用户信息
        R<LoginUser> userResult = remoteUserService.getUserInfo(username, SecurityConstants.INNER);

        if (R.SUCCESS != userResult.getCode())
        {
            log.error("login error, message={}", userResult.getMsg());
            throw exception0(userResult.getCode(), userResult.getMsg());
        }

        LoginUser userInfo = userResult.getData();
        SysUser user = userResult.getData().getSysUser();
        if (CommonStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            throwException(username, exception(ErrorCodeConstants.USER_IS_DELETE));
        }
        if (CommonStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            throwException(username, exception(ErrorCodeConstants.USER_IS_DISABLE));
        }
        passwordService.validate(user, password);
        // 初始化当前登录用户运营商
        Long operatorId = initOperatorId(user.getUserId());
        userInfo.setOperatorId(operatorId);
        userInfo.getSysUser().setOperatorId(operatorId);
        recordLoginInfo(user.getUserId());
        recordLogService.recordLogininfor(username, Constants.LOGIN_SUCCESS, "登录成功");
        return userInfo;
    }

    /**
     * 初始化运营商
     * @param userId 用户ID
     * @return Long
     */
    private Long initOperatorId(Long userId) {
        R<SysOperator> operatorR = remoteOperatorService.getOperatorInfoByUserId(userId, SecurityConstants.INNER);
        if (R.SUCCESS == operatorR.getCode()) {
            if (operatorR.getData() == null) {
                // 管理员的运营商ID为0
                return 0L;
            }
            SysOperator operator = operatorR.getData();
            return operator.getOperatorId();
        }
        log.warn("login => initOperatorId 初始化运营商失败, message={}", operatorR.getMsg());
        return 0L;
    }

    private void throwException(String username, ServiceException exception) {
        recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, ErrorCodeConstants.AUTH_LOGIN_USERNAME_NOT_IN_RANGE.getMsg());
        throw exception;
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId)
    {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        // 更新用户登录IP
        sysUser.setLoginIp(IpUtils.getIpAddr());
        // 更新用户登录时间
        sysUser.setLoginDate(DateUtils.getNowDate());
        remoteUserService.recordUserLogin(sysUser, SecurityConstants.INNER);
    }

    public void logout(String loginName)
    {
        recordLogService.recordLogininfor(loginName, Constants.LOGOUT, "退出成功");
    }

    /**
     * 注册
     */
    public void register(String username, String password)
    {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password))
        {
            throw exception(ErrorCodeConstants.AUTH_LOGIN_USERNAME_PASSWORD_NEED);
        }
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            throw exception(ErrorCodeConstants.AUTH_LOGIN_USERNAME_NOT_IN_RANGE);
        }
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            throw exception(ErrorCodeConstants.AUTH_LOGIN_PASSWORD_NOT_IN_RANGE);
        }

        // 注册用户信息
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser.setRealName(username);
        sysUser.setPassword(SecurityUtils.encryptPassword(password));
        R<?> registerResult = remoteUserService.registerUserInfo(sysUser, SecurityConstants.INNER);

        if (R.FAIL == registerResult.getCode())
        {
            throw exception(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
        recordLogService.recordLogininfor(username, Constants.REGISTER, "注册成功");
    }
}
