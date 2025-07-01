package com.things.cgomp.system.service.impl;

import com.things.cgomp.common.core.constant.UserConstants;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.system.api.domain.SysRole;
import com.things.cgomp.system.api.domain.SysUser;
import com.things.cgomp.system.service.ISysMenuService;
import com.things.cgomp.system.service.ISysPermissionService;
import com.things.cgomp.system.service.ISysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户权限处理
 * 
 * @author things
 */
@Service
public class SysPermissionServiceImpl implements ISysPermissionService
{
    @Resource
    private ISysRoleService roleService;

    @Resource
    private ISysMenuService menuService;

    /**
     * 获取角色数据权限
     * 
     * @param user 用户Id
     * @return 角色权限信息
     */
    @Override
    public Set<String> getRolePermission(SysUser user)
    {
        if (user == null) {
            return null;
        }
        Set<String> roles = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin())
        {
            roles.add("admin");
        }
        else
        {
            roles.addAll(roleService.selectRolePermissionByUserId(user.getUserId()));
        }
        return roles;
    }

    /**
     * 获取菜单数据权限
     * 
     * @param user 用户Id
     * @return 菜单权限信息
     */
    @Override
    public Set<String> getMenuPermission(SysUser user)
    {
        if (user == null) {
            return null;
        }
        Set<String> perms = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin())
        {
            perms.add("*:*:*");
        }
        else
        {
            List<SysRole> roles = user.getRoles();
            if (!CollectionUtils.isEmpty(roles))
            {
                // 多角色设置permissions属性，以便数据权限匹配权限
                for (SysRole role : roles)
                {
                    if (StringUtils.equals(role.getStatus(), UserConstants.ROLE_NORMAL))
                    {
                        Set<String> rolePerms = menuService.selectMenuPermsByRoleId(role.getRoleId());
                        role.setPermissions(rolePerms);
                        perms.addAll(rolePerms);
                    }
                }
            }
            else
            {
                perms.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
            }
        }
        return perms;
    }
}
