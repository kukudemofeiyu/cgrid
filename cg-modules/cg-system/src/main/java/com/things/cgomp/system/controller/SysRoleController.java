package com.things.cgomp.system.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.web.controller.BaseController;
import com.things.cgomp.common.core.web.domain.AjaxResult;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.log.enums.BusinessType;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.system.api.domain.SysRole;
import com.things.cgomp.system.convert.RoleConvert;
import com.things.cgomp.system.domain.dto.DeleteDTO;
import com.things.cgomp.system.enums.ErrorCodeConstants;
import com.things.cgomp.system.service.ISysOrgService;
import com.things.cgomp.system.service.ISysRoleService;
import com.things.cgomp.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 角色信息
 * 
 * @author things
 */
@Log(title = "角色管理")
@RestController
@RequestMapping("/role")
public class SysRoleController extends BaseController
{
    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysOrgService orgService;

    /**
     * 获取角色分页列表
     */
    @RequiresPermissions({
            "system:role:list",
            "system:role:platform:custom",
            "system:role:platform:predefine",
            "system:role:operator:custom",
            "system:role:operator:predefine"
    })
    @GetMapping("/page")
    public R<PageInfo<SysRole>> list(SysRole role)
    {
        startPage();
        PageInfo<SysRole> pageInfo = roleService.selectRolePage(role);
        return R.ok(pageInfo);
    }

    /**
     * 根据角色编号获取详细信息
     */
    @RequiresPermissions({
            "system:role:query",
            "system:role:platform:custom:query",
            "system:role:platform:predefine:query",
            "system:role:operator:custom:query",
            "system:role:operator:predefine:query"
    })
    @GetMapping(value = "")
    public R<SysRole> getInfo(SysRole req)
    {
        roleService.checkRoleDataScope(req.getRoleId());
        return R.ok(roleService.selectRoleInfo(req.getRoleId()));
    }

    /**
     * 新增角色
     */
    @RequiresPermissions({
            "system:role:add",
            "system:role:platform:custom:add",
            "system:role:operator:custom:query",
    })
    @Log(method = "新增角色", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@Validated @RequestBody SysRole role)
    {
        if (!roleService.checkRoleNameUnique(role))
        {
            throw exception(ErrorCodeConstants.ROLE_NAME_IS_EXIST);
        }
        return R.ok(roleService.insertRole(role));

    }

    /**
     * 修改保存角色
     */
    @RequiresPermissions({
            "system:role:edit",
            "system:role:platform:custom:edit",
            "system:role:operator:custom:edit",
    })
    @Log(method = "修改角色", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@Validated @RequestBody SysRole role)
    {
        roleService.checkRoleAllowed(role);
        roleService.checkRoleDataScope(role.getRoleId());
        if (!roleService.checkRoleNameUnique(role))
        {
            throw exception(ErrorCodeConstants.ROLE_NAME_IS_EXIST);
        }
        role.setUpdateBy(SecurityUtils.getUserId());;
        return R.ok(roleService.updateRole(role));
    }

    /**
     * 状态修改
     */
    @RequiresPermissions({
            "system:role:edit",
            "system:role:platform:custom:edit",
            "system:role:operator:custom:edit",
    })
    @Log(method = "修改角色状态", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public R<Integer> changeStatus(@RequestBody SysRole role)
    {
        roleService.checkRoleAllowed(role);
        roleService.checkRoleDataScope(role.getRoleId());
        role.setUpdateBy(SecurityUtils.getUserId());;
        return R.ok(roleService.updateRoleStatus(role));
    }

    /**
     * 删除角色
     */
    @RequiresPermissions({
            "system:role:remove",
            "system:role:platform:custom:remove",
            "system:role:operator:custom:remove",
    })
    @Log(method = "删除角色", businessType = BusinessType.DELETE)
    @DeleteMapping()
    public R<Integer> remove(@Validated @RequestBody DeleteDTO deleteDto)
    {
        return R.ok(roleService.deleteRoleByIds(deleteDto.getIds()));
    }

    /**
     * 获取角色选择框列表
     */
    @RequiresPermissions("system:role:query")
    @GetMapping("/simple-list")
    public AjaxResult simpleList(@RequestParam(value = "orgType") Integer orgType)
    {
        SysRole roleReq = new SysRole().setOrgType(orgType);
        List<SysRole> roles = roleService.selectRoleList(roleReq);
        return success(RoleConvert.INSTANCE.convertList(roles));
    }
}
