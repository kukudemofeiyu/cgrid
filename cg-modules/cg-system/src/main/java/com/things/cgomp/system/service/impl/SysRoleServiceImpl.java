package com.things.cgomp.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.constant.UserConstants;
import com.things.cgomp.common.core.enums.OrgTypeEnum;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.core.utils.SpringUtils;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.datascope.enums.DataScopeEnum;
import com.things.cgomp.common.security.service.TokenService;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.system.api.domain.SysRole;
import com.things.cgomp.system.api.domain.SysUser;
import com.things.cgomp.system.api.domain.SysUserRole;
import com.things.cgomp.system.domain.SysRoleMenu;
import com.things.cgomp.system.domain.SysRoleOrg;
import com.things.cgomp.system.enums.ErrorCodeConstants;
import com.things.cgomp.system.mapper.SysRoleMapper;
import com.things.cgomp.system.mapper.SysRoleMenuMapper;
import com.things.cgomp.system.mapper.SysRoleOrgMapper;
import com.things.cgomp.system.mapper.SysUserRoleMapper;
import com.things.cgomp.system.service.ISysMenuService;
import com.things.cgomp.system.service.ISysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.things.cgomp.common.core.utils.PageUtils.startPage;

/**
 * 角色 业务层处理
 *
 * @author things
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService
{
    @Resource
    private SysRoleMapper roleMapper;

    @Resource
    private SysRoleMenuMapper roleMenuMapper;

    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Resource
    private SysRoleOrgMapper roleDeptMapper;

    @Resource
    private ISysMenuService menuService;
    @Resource
    private TokenService tokenService;

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    @DataScope(orgAlias = "o")
    public List<SysRole> selectRoleList(SysRole role)
    {
        if (!SecurityUtils.isAdmin()) {
            // 非管理员只返回可允许选择的角色
            role.setCheckEnable(true);
        }
        return roleMapper.selectRoleList(role);
    }

    @Override
    @DataScope(orgAlias = "o")
    public PageInfo<SysRole> selectRolePage(SysRole role)
    {
        startPage();
        List<SysRole> list = roleMapper.selectRoleList(role);
        return new PageInfo<>(list);
    }

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRolesByUserId(Long userId)
    {
        List<SysRole> userRoles = roleMapper.selectRolePermissionByUserId(userId);
        List<SysRole> roles = selectRoleAll();
        for (SysRole role : roles)
        {
            for (SysRole userRole : userRoles)
            {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue())
                {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    @Override
    public List<SysRole> selectRoles(Long userId) {
        List<SysRole> roles = selectRoleAll();
        return SecurityUtils.isAdmin(userId) ? roles :
                roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList());
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId)
    {
        List<SysRole> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms)
        {
            if (StringUtils.isNotNull(perm))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleCode().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRoleAll()
    {
        return SpringUtils.getAopProxy(this).selectRoleList(new SysRole());
    }

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    @Override
    public List<Long> selectRoleListByUserId(Long userId)
    {
        return roleMapper.selectRoleListByUserId(userId);
    }

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public SysRole selectRoleById(Long roleId)
    {
        return roleMapper.selectRoleById(roleId);
    }

    @Override
    public SysRole selectRoleInfo(Long roleId) {
        List<Long> menuIds = menuService.selectMenuListByRoleId(roleId);
        SysRole role = roleMapper.selectRoleById(roleId);
        List<Long> halfMenuIds = JSON.parseArray(role.getHalfMenuIdConfig(), Long.class);
        role.setHalfMenuIds(halfMenuIds == null ? new Long[0] : halfMenuIds.toArray(new Long[0]));
        role.setMenuIds(menuIds.toArray(new Long[0]));
        return role;
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public boolean checkRoleNameUnique(SysRole role)
    {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public boolean checkRoleKeyUnique(SysRole role)
    {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = roleMapper.checkRoleKeyUnique(role.getRoleCode());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色是否允许操作
     *
     * @param role 角色信息
     */
    @Override
    public void checkRoleAllowed(SysRole role)
    {
        if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin())
        {
            throw exception(ErrorCodeConstants.ROLE_SUPER_ADMIN_CANT_NOT_OPERATOR);
        }
        SysRole dbRole = roleMapper.selectById(role.getRoleId());
        if (dbRole.getType().equals(0)) {
            throw exception(ErrorCodeConstants.ROLE_BUILTIN_CANT_NOT_OPERATOR);
        }
    }

    /**
     * 校验角色是否有数据权限
     *
     * @param roleIds 角色id
     */
    @Override
    public void checkRoleDataScope(Long... roleIds)
    {
        if (roleIds == null) {
            return;
        }
        if (!SysUser.isAdmin(SecurityUtils.getUserId()))
        {
            for (Long roleId : roleIds)
            {
                SysRole role = new SysRole();
                role.setRoleId(roleId);
                List<SysRole> roles = SpringUtils.getAopProxy(this).selectRoleList(role);
                if (StringUtils.isEmpty(roles))
                {
                    throw new ServiceException("没有权限访问角色数据！");
                }
            }
        }
    }

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int countUserRoleByRoleId(Long roleId)
    {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }

    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertRole(SysRole role)
    {
        // 新增角色信息
        role.setHalfMenuIdConfig();
        role.setOrgId(SecurityUtils.getOrgId());
        role.setCreateBy(SecurityUtils.getUserId());
        role.setDataScope(OrgTypeEnum.PLATFORM.getType().equals(role.getOrgType())
                ? DataScopeEnum.CUSTOM_PLATFORM_ROLE.getCode()
                : DataScopeEnum.CUSTOM_OPERATOR_ROLE.getCode());
        roleMapper.insertRole(role);
        return insertRoleMenu(role);
    }

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int updateRole(SysRole role)
    {
        // 修改角色信息
        role.setHalfMenuIdConfig();
        roleMapper.updateRole(role);
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        int rows = insertRoleMenu(role);
        tokenService.updateUserSession(role);
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRoleMenu(SysRole role) {
        if (role.getMenuIds() == null) {
            throw exception(ErrorCodeConstants.ROLE_MENU_IDS_NOT_NULL);
        }
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        return insertRoleMenu(role);
    }

    /**
     * 修改角色状态
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int updateRoleStatus(SysRole role)
    {
        return roleMapper.updateRole(role);
    }

    /**
     * 修改数据权限信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int authDataScope(SysRole role)
    {
        // 修改角色信息
        roleMapper.updateRole(role);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleOrgByRoleId(role.getRoleId());
        // 新增角色和部门信息（数据权限）
        return insertRoleOrg(role);
    }

    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public int insertRoleMenu(SysRole role)
    {
        int rows = 1;
        // 新增用户与角色管理
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (Long menuId : role.getMenuIds())
        {
            if (menuId == null) {
                continue;
            }
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (!list.isEmpty())
        {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 新增角色部门信息(数据权限)
     *
     * @param role 角色对象
     */
    public int insertRoleOrg(SysRole role)
    {
        int rows = 1;
        // 新增角色与部门（数据权限）管理
        List<SysRoleOrg> list = new ArrayList<SysRoleOrg>();
        for (Long deptId : role.getOrgIds())
        {
            SysRoleOrg rd = new SysRoleOrg();
            rd.setRoleId(role.getRoleId());
            rd.setOrgId(deptId);
            list.add(rd);
        }
        if (!list.isEmpty())
        {
            rows = roleDeptMapper.batchRoleOrg(list);
        }
        return rows;
    }

    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleById(Long roleId)
    {
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(roleId);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleOrgByRoleId(roleId);
        return roleMapper.deleteRoleById(roleId);
    }

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleByIds(Long[] roleIds)
    {
        for (Long roleId : roleIds)
        {
            checkRoleAllowed(new SysRole(roleId));
            checkRoleDataScope(roleId);
            SysRole role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0)
            {
                throw exception(ErrorCodeConstants.ROLE_IS_ASSIGN, role.getRoleName());
            }
        }
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenu(roleIds);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleOrg(roleIds);
        return roleMapper.deleteRoleByIds(roleIds);
    }

    /**
     * 取消授权用户角色
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    @Override
    public int deleteAuthUser(SysUserRole userRole)
    {
        return userRoleMapper.deleteUserRoleInfo(userRole);
    }

    /**
     * 批量取消授权用户角色
     *
     * @param roleId 角色ID
     * @param userIds 需要取消授权的用户数据ID
     * @return 结果
     */
    @Override
    public int deleteAuthUsers(Long roleId, Long[] userIds)
    {
        return userRoleMapper.deleteUserRoleInfos(roleId, userIds);
    }

    /**
     * 批量选择授权用户角色
     *
     * @param roleId 角色ID
     * @param userIds 需要授权的用户数据ID
     * @return 结果
     */
    @Override
    public int insertAuthUsers(Long roleId, Long[] userIds)
    {
        // 新增用户与角色管理
        List<SysUserRole> list = new ArrayList<SysUserRole>();
        for (Long userId : userIds)
        {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        return userRoleMapper.batchUserRole(list);
    }

    @Override
    @DataScope(orgAlias = "r")
    public List<SysRole> selectBuiltInRoleByOrgType(SysRole role) {
        return roleMapper.selectRoleByTypeAndOrgType(role);
    }
}
