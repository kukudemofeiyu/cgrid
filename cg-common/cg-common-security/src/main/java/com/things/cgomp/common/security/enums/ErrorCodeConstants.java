package com.things.cgomp.common.security.enums;


import com.things.cgomp.common.core.exception.ErrorCode;

/**
 * SECURITY 错误码枚举类
 *
 * security 错误码区间 [1-100-000-000 ~ 1-101-000-000)
 */
public interface ErrorCodeConstants {

    ErrorCode INNER_AUTH_NOT_ALLOW = new ErrorCode(1_100_001_000, "没有内部访问权限，不允许访问");
    ErrorCode INNER_AUTH_NO_USER = new ErrorCode(1_100_001_001, "没有设置用户信息，不允许访问");
}
