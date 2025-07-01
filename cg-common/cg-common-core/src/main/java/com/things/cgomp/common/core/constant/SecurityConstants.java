package com.things.cgomp.common.core.constant;

/**
 * 权限相关通用常量
 * 
 * @author things
 */
public class SecurityConstants
{
    /**
     * 租户ID字段
     */
    public static final String DETAILS_TENANT_ID = "tenant_id";
    /**
     * 组织ID字段
     */
    public static final String DETAILS_ORG_ID = "org_id";
    /**
     * 用户ID字段
     */
    public static final String DETAILS_USER_ID = "user_id";
    /**
     * 运营商ID字段
     */
    public static final String DETAILS_OPERATOR_ID = "operator_id";

    /**
     * 用户名字段
     */
    public static final String DETAILS_USERNAME = "username";

    /**
     * 授权信息字段
     */
    public static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * 请求来源
     */
    public static final String FROM_SOURCE = "from-source";

    /**
     * 内部请求
     */
    public static final String INNER = "inner";

    /**
     * 用户标识
     */
    public static final String USER_KEY = "user_key";

    /**
     * 登录用户
     */
    public static final String LOGIN_USER = "login_user";

    /**
     * 角色权限
     */
    public static final String ROLE_PERMISSION = "role_permission";
    /**
     * 授权信息字段
     */
    public static final String REMOTE_HEADER = "remoteheader";
    /**
     * 组织根节点ID
     */
    public static final Long ROOT_ORG_ID = 0L;
    /**
     * 数据过滤全部权限（运营商、站点维度）
     */
    public static final Integer DATA_SCOPE_RANGE_ALL = 1;
    /**
     * 数据过滤部分权限（运营商、站点维度）
     */
    public static final Integer DATA_SCOPE_RANGE_PART = 2;

}
