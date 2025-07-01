package com.things.cgomp.system.api.domain;

import lombok.Data;

/**
 * 用户和角色关联 system_user_role
 * 
 * @author things
 */
@Data
public class SysUserRole
{
    /** 用户ID */
    private Long userId;

    /** 角色ID */
    private Long roleId;

    /** 租户ID */
    private Long tenantId;
}
