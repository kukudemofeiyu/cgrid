package com.things.cgomp.system.mapper;

import com.things.cgomp.system.domain.SysRoleOrg;

import java.util.List;

/**
 * 角色与组织关联表 数据层
 * 
 * @author things
 */
public interface SysRoleOrgMapper
{
    /**
     * 通过角色ID删除角色和部门关联
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteRoleOrgByRoleId(Long roleId);

    /**
     * 批量删除角色部门关联信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRoleOrg(Long[] ids);

    /**
     * 查询部门使用数量
     * 
     * @param orgId 组织ID
     * @return 结果
     */
    public int selectCountRoleOrgByOrgId(Long orgId);

    /**
     * 批量新增角色部门信息
     * 
     * @param roleOrgList 角色部门列表
     * @return 结果
     */
    public int batchRoleOrg(List<SysRoleOrg> roleOrgList);
}
