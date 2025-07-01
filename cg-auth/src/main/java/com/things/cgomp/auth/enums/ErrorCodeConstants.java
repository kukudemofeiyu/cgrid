package com.things.cgomp.auth.enums;


import com.things.cgomp.common.core.exception.ErrorCode;

/**
 * AUTH 错误码枚举类
 *
 * auth 模块，使用 1-001-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== AUTH 模块 1-001-000-000 ==========
    ErrorCode AUTH_LOGIN_USERNAME_PASSWORD_NEED = new ErrorCode(1_001_000_000, "用户/密码必须填写");
    ErrorCode AUTH_LOGIN_PASSWORD_NOT_IN_RANGE = new ErrorCode(1_001_000_001, "用户密码不在指定范围");
    ErrorCode AUTH_LOGIN_USERNAME_NOT_IN_RANGE = new ErrorCode(1_001_000_002, "用户名不在指定范围");
    ErrorCode AUTH_LOGIN_IP_IN_BLACKLIST = new ErrorCode(1_001_000_003, "很遗憾，访问IP已被列入系统黑名单");
    ErrorCode USER_IS_DELETE = new ErrorCode(1_001_000_004, "名字为【{}】的用户已被删除");
    ErrorCode USER_IS_DISABLE = new ErrorCode(1_001_000_005, "名字为【{}】的用户已被禁用");
    ErrorCode USER_LOGIN_LOCK = new ErrorCode(1_001_000_006, "密码输入错误{}次，帐户锁定{}分钟");
    ErrorCode AUTH_LOGIN_BAD_CREDENTIALS = new ErrorCode(1_001_000_007, "登录失败，账号密码不正确");

    ErrorCode LOGIN_FAIL = new ErrorCode(1_001_500_001, "登录失败");
}
