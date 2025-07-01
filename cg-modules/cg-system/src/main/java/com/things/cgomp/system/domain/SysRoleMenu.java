package com.things.cgomp.system.domain;

import lombok.Data;

/**
 * 角色和菜单关联 system_role_menu
 * 
 * @author things
 */
@Data
public class SysRoleMenu
{
    /** 角色ID */
    private Long roleId;

    /** 菜单ID */
    private Long menuId;

    /** 租户ID */
    private Long tenantId;
}
