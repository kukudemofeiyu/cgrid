package com.things.cgomp.common.core.context;

/**
 * 认证模块服务接口
 * 提供用于模块下无法直接引用调用 com.things.cgomp.common.security.utils.SecurityUtils
 */
public interface ISecurityContext {

    Long getUserId();

    Long getTenantId();
}
