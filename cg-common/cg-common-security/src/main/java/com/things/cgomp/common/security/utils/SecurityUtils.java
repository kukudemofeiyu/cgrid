package com.things.cgomp.common.security.utils;

import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.constant.TokenConstants;
import com.things.cgomp.common.core.context.SecurityContextHolder;
import com.things.cgomp.common.core.enums.OrgTypeEnum;
import com.things.cgomp.common.core.utils.ServletUtils;
import com.things.cgomp.common.security.auth.AuthUtil;
import com.things.cgomp.system.api.model.LoginUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限获取工具类
 *
 * @author things
 */
public class SecurityUtils {
    /**
     * 获取租户ID
     */
    public static Long getTenantId() {
        LoginUser loginUser = getLoginUser();
        if (loginUser == null) {
            return 0L;
        }
        return loginUser.getTenantId();
    }

    /**
     * 获取组织ID
     */
    public static Long getOrgId() {
        LoginUser loginUser = getLoginUser();
        if (loginUser == null || loginUser.getSysUser() == null) {
            return 0L;
        }
        return loginUser.getSysUser().getOrgId();
    }


    /**
     * 获取用户ID
     */
    public static Long getUserId() {
        LoginUser loginUser = getLoginUser();
        if (loginUser == null) {
            return 0L;
        }
        return loginUser.getUserid();
    }

    /**
     * 获取用户名称
     */
    public static String getUsername() {
        LoginUser loginUser = getLoginUser();
        if (loginUser == null) {
            return StringUtils.EMPTY;
        }
        return loginUser.getUsername();
    }

    /**
     * 获取运营商ID
     */
    public static Long getOperatorId(){
        LoginUser loginUser = getLoginUser();
        if (loginUser == null) {
            return -1L;
        }
        return loginUser.getOperatorId();
    }

    /**
     * 获取请求token
     */
    public static String getToken() {
        HttpServletRequest request = ServletUtils.getRequest();
        if (request == null) {
            return null;
        }
        return getToken(request);
    }

    /**
     * 根据request获取请求token
     */
    public static String getToken(HttpServletRequest request) {
        // 从header获取token标识
        String token = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
        return replaceTokenPrefix(token);
    }

    /**
     * 裁剪token前缀
     */
    public static String replaceTokenPrefix(String token) {
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX)) {
            token = token.replaceFirst(TokenConstants.PREFIX, "");
        }
        return token;
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    public static boolean isAdmin() {
        return isAdmin(getUserId());
    }

    /**
     * 是否为运营商
     * @return 结果
     */
    public static boolean isOperator(){
        LoginUser loginUser = getLoginUser();
        if (loginUser == null || loginUser.getSysUser() == null) {
            return false;
        }
        return OrgTypeEnum.OPERATOR.getType().equals(loginUser.getSysUser().getOrgType());
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 获取登录用户信息
     */
    public static LoginUser getLoginUser() {
        return SecurityContextHolder.get(SecurityConstants.LOGIN_USER, LoginUser.class);
    }

    /**
     * 获取站点id列表
     */
    public static List<Long> getSiteIds() {
        Long userId = getUserId();
        if (userId == null) {
            return new ArrayList<>();
        }
        return AuthUtil.authLogic.getSiteIds(userId);
    }

    public static boolean isRootOrg(Long orgId){
        return SecurityConstants.ROOT_ORG_ID.equals(orgId);
    }
}
