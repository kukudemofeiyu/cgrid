package com.things.cgomp.system.controller;

import com.things.cgomp.common.core.constant.UserConstants;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.core.web.controller.BaseController;
import com.things.cgomp.common.core.web.domain.AjaxResult;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.log.enums.BusinessType;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.system.api.domain.SysOrg;
import com.things.cgomp.system.service.ISysOrgService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 组织信息
 * 
 * @author things
 */
@RestController
@RequestMapping("/org")
public class SysOrgController extends BaseController
{
    @Autowired
    private ISysOrgService orgService;

    /**
     * 获取组织列表
     */
    @RequiresPermissions("system:org:list")
    @GetMapping("/list")
    public AjaxResult list(SysOrg org)
    {
        List<SysOrg> orgs = orgService.selectOrgList(org);
        return success(org);
    }

    /**
     * 查询组织列表（排除节点）
     */
    @RequiresPermissions("system:org:list")
    @GetMapping("/list/exclude/{deptId}")
    public AjaxResult excludeChild(@PathVariable(value = "deptId", required = false) Long deptId)
    {
        List<SysOrg> orgs = orgService.selectOrgList(new SysOrg());
        orgs.removeIf(d -> d.getOrgId().intValue() == deptId || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + ""));
        return success(orgs);
    }

    /**
     * 根据组织编号获取详细信息
     */
    @RequiresPermissions("system:org:query")
    @GetMapping(value = "/{orgId}")
    public AjaxResult getInfo(@PathVariable Long orgId)
    {
        orgService.checkOrgDataScope(orgId);
        return success(orgService.selectOrgById(orgId));
    }

    /**
     * 新增组织
     */
    @RequiresPermissions("system:org:add")
    @Log(title = "组织管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysOrg org)
    {
        if (!orgService.checkOrgNameUnique(org))
        {
            return error("新增组织'" + org.getOrgName() + "'失败，组织名称已存在");
        }
        org.setCreateBy(SecurityUtils.getUserId());
        return toAjax(orgService.insertOrg(org));
    }

    /**
     * 修改组织
     */
    @RequiresPermissions("system:org:edit")
    @Log(title = "组织管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysOrg org)
    {
        Long deptId = org.getOrgId();
        orgService.checkOrgDataScope(deptId);
        if (!orgService.checkOrgNameUnique(org))
        {
            return error("修改组织'" + org.getOrgName() + "'失败，组织名称已存在");
        }
        else if (org.getParentId().equals(deptId))
        {
            return error("修改组织'" + org.getOrgName() + "'失败，上级组织不能是自己");
        }
        else if (StringUtils.equals(UserConstants.DEPT_DISABLE, org.getStatus()) && orgService.selectNormalChildrenOrgById(deptId) > 0)
        {
            return error("该组织包含未停用的子组织！");
        }
        org.setUpdateBy(SecurityUtils.getUserId());;
        return toAjax(orgService.updateOrg(org));
    }

    /**
     * 删除组织
     */
    @RequiresPermissions("system:org:remove")
    @Log(title = "组织管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}")
    public AjaxResult remove(@PathVariable Long deptId)
    {
        if (orgService.hasChildByOrgId(deptId))
        {
            return warn("存在下级组织,不允许删除");
        }
        if (orgService.checkOrgExistUser(deptId))
        {
            return warn("组织存在用户,不允许删除");
        }
        orgService.checkOrgDataScope(deptId);
        return toAjax(orgService.deleteOrgById(deptId));
    }
}
