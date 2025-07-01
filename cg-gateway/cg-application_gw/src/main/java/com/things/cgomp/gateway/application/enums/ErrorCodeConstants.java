package com.things.cgomp.gateway.application.enums;


import com.things.cgomp.common.core.exception.ErrorCode;

/**
 * APPLICATION-GW 错误码枚举类
 *
 * 服务网关 模块，使用 1-002-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== 网关相关 1-002-000-000 ==========
    ErrorCode GW_SERVICE_NOT_FOUNT  = new ErrorCode(1_002_000_001, "服务未找到");
    ErrorCode GW_INNER_SERVER_ERROR  = new ErrorCode(1_002_000_002, "内部服务器错误");

    // ========== 认证相关 1-002-001-000 ==========
    ErrorCode CAPTCHA_CODE_NOT_NULL = new ErrorCode(1_002_001_000, "验证码不能为空");
    ErrorCode CAPTCHA_CODE_IS_EXPIRED = new ErrorCode(1_002_001_001, "验证码已失效");
    ErrorCode CAPTCHA_CODE_ERROR = new ErrorCode(1_002_001_002, "验证码错误");
    ErrorCode AUTH_TOKEN_NOT_NULL = new ErrorCode(1_002_001_003, "令牌不能为空");
    ErrorCode AUTH_TOKEN_IS_INCORRECT = new ErrorCode(1_002_001_004, "令牌已过期或验证不正确！");
    ErrorCode AUTH_TOKEN_IS_EXPIRED = new ErrorCode(1_002_001_005, "登录状态已过期");
    ErrorCode AUTH_TOKEN_VERIFY_FAIL = new ErrorCode(1_002_001_006, "令牌验证失败");

    // ========== 请求相关 1-002-003-000 ==========
    ErrorCode REQUEST_EXCEED_MAX_LIMIT = new ErrorCode(1_002_003_000, "请求超过最大数，请稍候再试");
    ErrorCode REQUEST_ADDR_NOT_ALLOW = new ErrorCode(1_002_003_001, "请求地址不允许访问");
}
