package com.things.cgomp.system.service;

import com.things.cgomp.system.api.domain.SysOrg;
import com.things.cgomp.system.domain.vo.TreeSelect;

import java.util.List;

/**
 * 组织管理 服务层
 * 
 * @author things
 */
public interface ISysOrgService
{
    /**
     * 查询组织管理数据
     * 
     * @param org 组织信息
     * @return 部门信息集合
     */
    List<SysOrg> selectOrgList(SysOrg org);

    /**
     * 查询部门树结构信息
     * 
     * @param org 组织信息
     * @return 部门树信息集合
     */
    List<TreeSelect> selectOrgTreeList(SysOrg org);

    /**
     * 构建前端所需要树结构
     * 
     * @param orgs 组织列表
     * @return 树结构列表
     */
    List<SysOrg> buildOrgTree(List<SysOrg> orgs);

    /**
     * 构建前端所需要下拉树结构
     * 
     * @param orgs 组织列表
     * @return 下拉树结构列表
     */
    List<TreeSelect> buildOrgTreeSelect(List<SysOrg> orgs);

    /**
     * 根据角色ID查询组织树信息
     * 
     * @param roleId 角色ID
     * @return 选中组织列表
     */
    List<Long> selectOrgListByRoleId(Long roleId);

    /**
     * 根据组织ID查询信息
     * 
     * @param orgId 组织ID
     * @return 部门信息
     */
    SysOrg selectOrgById(Long orgId);

    /**
     * 根据ID查询所有子组织（正常状态）
     * 
     * @param orgId 组织ID
     * @return 子组织数
     */
    int selectNormalChildrenOrgById(Long orgId);

    /**
     * 是否存在组织子节点
     * 
     * @param orgId 组织ID
     * @return 结果
     */
    boolean hasChildByOrgId(Long orgId);

    /**
     * 查询组织是否存在用户
     * 
     * @param orgId 组织ID
     * @return 结果 true 存在 false 不存在
     */
    boolean checkOrgExistUser(Long orgId);

    /**
     * 校验组织名称是否唯一
     * 
     * @param org 组织信息
     * @return 结果
     */
    boolean checkOrgNameUnique(SysOrg org);

    /**
     * 校验组织是否有数据权限
     * 
     * @param orgId 组织ID
     */
    void checkOrgDataScope(Long orgId);

    /**
     * 新增保存组织信息
     * 
     * @param org 组织信息
     * @return 结果
     */
    int insertOrg(SysOrg org);

    /**
     * 修改保存组织信息
     * 
     * @param org 组织信息
     * @return 结果
     */
    int updateOrg(SysOrg org);

    /**
     * 删除部门管理信息
     * 
     * @param orgId 组织ID
     * @return 结果
     */
    int deleteOrgById(Long orgId);
}
