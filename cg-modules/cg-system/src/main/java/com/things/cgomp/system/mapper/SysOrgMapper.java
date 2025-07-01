package com.things.cgomp.system.mapper;

import com.things.cgomp.system.api.domain.SysOrg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门管理 数据层
 * 
 * @author things
 */
public interface SysOrgMapper
{
    /**
     * 查询部门管理数据
     * 
     * @param org 部门信息
     * @return 部门信息集合
     */
    public List<SysOrg> selectOrgList(SysOrg org);

    /**
     * 根据角色ID查询部门树信息
     * 
     * @param roleId 角色ID
     * @param orgCheckStrictly 部门树选择项是否关联显示
     * @return 选中部门列表
     */
    public List<Long> selectOrgListByRoleId(@Param("roleId") Long roleId, @Param("orgCheckStrictly") boolean orgCheckStrictly);

    /**
     * 根据部门ID查询信息
     * 
     * @param OrgId 部门ID
     * @return 部门信息
     */
    public SysOrg selectOrgById(Long OrgId);

    /**
     * 根据ID查询所有子部门
     * 
     * @param orgId 部门ID
     * @return 部门列表
     */
    public List<SysOrg> selectChildrenOrgById(Long orgId);

    /**
     * 根据ID查询所有子部门（正常状态）
     * 
     * @param orgId 部门ID
     * @return 子部门数
     */
    public int selectNormalChildrenOrgById(Long orgId);

    /**
     * 是否存在子节点
     * 
     * @param orgId 部门ID
     * @return 结果
     */
    public int hasChildByOrgId(Long orgId);

    /**
     * 查询部门是否存在用户
     * 
     * @param orgId 部门ID
     * @return 结果
     */
    public int checkOrgExistUser(Long orgId);

    /**
     * 校验部门名称是否唯一
     * 
     * @param orgName 部门名称
     * @param parentId 父部门ID
     * @return 结果
     */
    public SysOrg checkOrgNameUnique(@Param("deptName") String orgName, @Param("parentId") Long parentId);

    /**
     * 新增部门信息
     * 
     * @param org 部门信息
     * @return 结果
     */
    public int insertOrg(SysOrg org);

    /**
     * 修改部门信息
     * 
     * @param org 部门信息
     * @return 结果
     */
    public int updateOrg(SysOrg org);

    /**
     * 修改所在部门正常状态
     * 
     * @param orgIds 部门ID组
     */
    public void updateOrgStatusNormal(Long[] orgIds);

    /**
     * 修改子元素关系
     * 
     * @param orgs 子元素
     * @return 结果
     */
    public int updateOrgChildren(@Param("depts") List<SysOrg> orgs);

    /**
     * 删除部门管理信息
     * 
     * @param orgId 部门ID
     * @return 结果
     */
    public int deleteOrgById(Long orgId);
}
