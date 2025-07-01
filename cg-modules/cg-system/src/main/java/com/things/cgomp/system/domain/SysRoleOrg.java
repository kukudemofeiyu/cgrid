package com.things.cgomp.system.domain;

import lombok.Data;

/**
 * 角色和组织关联 system_role_org
 * 
 * @author things
 */
@Data
public class SysRoleOrg
{
    /** 角色ID */
    private Long roleId;
    
    /** 组织ID */
    private Long orgId;

    /** 租户ID */
    private Long tenantId;
}
