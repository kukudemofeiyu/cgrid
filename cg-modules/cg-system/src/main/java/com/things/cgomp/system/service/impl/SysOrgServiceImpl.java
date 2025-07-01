package com.things.cgomp.system.service.impl;

import com.things.cgomp.common.core.constant.UserConstants;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.core.text.Convert;
import com.things.cgomp.common.core.utils.SpringUtils;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.system.api.domain.SysOrg;
import com.things.cgomp.system.api.domain.SysRole;
import com.things.cgomp.system.api.domain.SysUser;
import com.things.cgomp.system.domain.vo.TreeSelect;
import com.things.cgomp.system.enums.ErrorCodeConstants;
import com.things.cgomp.system.mapper.SysOrgMapper;
import com.things.cgomp.system.mapper.SysRoleMapper;
import com.things.cgomp.system.service.ISysOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 部门管理 服务实现
 * 
 * @author things
 */
@Service
public class SysOrgServiceImpl implements ISysOrgService
{
    @Autowired
    private SysOrgMapper orgMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    /**
     * 查询部门管理数据
     * 
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    @DataScope(orgAlias = "o")
    public List<SysOrg> selectOrgList(SysOrg dept)
    {
        return orgMapper.selectOrgList(dept);
    }

    /**
     * 查询部门树结构信息
     * 
     * @param dept 部门信息
     * @return 部门树信息集合
     */
    @Override
    public List<TreeSelect> selectOrgTreeList(SysOrg dept)
    {
        List<SysOrg> orgList = SpringUtils.getAopProxy(this).selectOrgList(dept);
        return buildOrgTreeSelect(orgList);
    }

    /**
     * 构建前端所需要树结构
     * 
     * @param depts 部门列表
     * @return 树结构列表
     */
    @Override
    public List<SysOrg> buildOrgTree(List<SysOrg> depts)
    {
        List<SysOrg> returnList = new ArrayList<SysOrg>();
        List<Long> tempList = depts.stream().map(SysOrg::getOrgId).collect(Collectors.toList());
        for (SysOrg dept : depts)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId()))
            {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     * 
     * @param orgs 组织列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildOrgTreeSelect(List<SysOrg> orgs)
    {
        List<SysOrg> orgTrees = buildOrgTree(orgs);
        return orgTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据角色ID查询部门树信息
     * 
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    @Override
    public List<Long> selectOrgListByRoleId(Long roleId)
    {
        SysRole role = roleMapper.selectRoleById(roleId);
        return orgMapper.selectOrgListByRoleId(roleId, role.isOrgCheckStrictly());
    }

    /**
     * 根据部门ID查询信息
     * 
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public SysOrg selectOrgById(Long deptId)
    {
        return orgMapper.selectOrgById(deptId);
    }

    /**
     * 根据ID查询所有子部门（正常状态）
     * 
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Override
    public int selectNormalChildrenOrgById(Long deptId)
    {
        return orgMapper.selectNormalChildrenOrgById(deptId);
    }

    /**
     * 是否存在子节点
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public boolean hasChildByOrgId(Long deptId)
    {
        int result = orgMapper.hasChildByOrgId(deptId);
        return result > 0;
    }

    /**
     * 查询部门是否存在用户
     * 
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkOrgExistUser(Long deptId)
    {
        int result = orgMapper.checkOrgExistUser(deptId);
        return result > 0;
    }

    /**
     * 校验部门名称是否唯一
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public boolean checkOrgNameUnique(SysOrg dept)
    {
        Long deptId = StringUtils.isNull(dept.getOrgId()) ? -1L : dept.getOrgId();
        SysOrg info = orgMapper.checkOrgNameUnique(dept.getOrgName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getOrgId().longValue() != deptId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验组织是否有数据权限
     * 
     * @param orgId 组织id
     */
    @Override
    public void checkOrgDataScope(Long orgId)
    {
        if (!SysUser.isAdmin(SecurityUtils.getUserId()) && StringUtils.isNotNull(orgId))
        {
            SysOrg org = new SysOrg();
            org.setOrgId(orgId);
            List<SysOrg> orgs = SpringUtils.getAopProxy(this).selectOrgList(org);
            if (StringUtils.isEmpty(orgs))
            {
                throw new ServiceException("没有权限访问组织数据！");
            }
        }
    }

    /**
     * 新增保存部门信息
     * 
     * @param org 组织信息
     * @return 结果
     */
    @Override
    public int insertOrg(SysOrg org)
    {
        if (SecurityUtils.isRootOrg(org.getParentId())) {
            org.setAncestors(String.valueOf(org.getParentId()));
        }else {
            SysOrg info = orgMapper.selectOrgById(org.getParentId());
            // 如果父节点不为正常状态,则不允许新增子节点
            if (!UserConstants.ORG_NORMAL.equals(info.getStatus()))
            {
                throw exception(ErrorCodeConstants.ORG_IS_DISABLE);
            }
            org.setAncestors(info.getAncestors() + "," + org.getParentId());
        }
        return orgMapper.insertOrg(org);
    }

    /**
     * 修改保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int updateOrg(SysOrg dept)
    {
        SysOrg newParentDept = orgMapper.selectOrgById(dept.getParentId());
        SysOrg oldDept = orgMapper.selectOrgById(dept.getOrgId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept))
        {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getOrgId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getOrgId(), newAncestors, oldAncestors);
        }
        int result = orgMapper.updateOrg(dept);
        if (UserConstants.ORG_NORMAL.equals(dept.getStatus()) && StringUtils.isNotEmpty(dept.getAncestors())
                && !StringUtils.equals("0", dept.getAncestors()))
        {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatusNormal(dept);
        }
        return result;
    }

    /**
     * 修改该部门的父级部门状态
     * 
     * @param dept 当前部门
     */
    private void updateParentDeptStatusNormal(SysOrg dept)
    {
        String ancestors = dept.getAncestors();
        Long[] deptIds = Convert.toLongArray(ancestors);
        orgMapper.updateOrgStatusNormal(deptIds);
    }

    /**
     * 修改子元素关系
     * 
     * @param deptId 被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors)
    {
        List<SysOrg> children = orgMapper.selectChildrenOrgById(deptId);
        for (SysOrg child : children)
        {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            orgMapper.updateOrgChildren(children);
        }
    }

    /**
     * 删除部门管理信息
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteOrgById(Long deptId)
    {
        return orgMapper.deleteOrgById(deptId);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysOrg> list, SysOrg t)
    {
        // 得到子节点列表
        List<SysOrg> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysOrg tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysOrg> getChildList(List<SysOrg> list, SysOrg t)
    {
        List<SysOrg> tlist = new ArrayList<SysOrg>();
        Iterator<SysOrg> it = list.iterator();
        while (it.hasNext())
        {
            SysOrg n = (SysOrg) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getOrgId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysOrg> list, SysOrg t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
