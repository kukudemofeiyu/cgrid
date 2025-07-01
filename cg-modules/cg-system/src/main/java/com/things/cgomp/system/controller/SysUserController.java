package com.things.cgomp.system.controller;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.core.utils.poi.ExcelUtil;
import com.things.cgomp.common.core.web.controller.BaseController;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.log.enums.BusinessType;
import com.things.cgomp.common.security.annotation.InnerAuth;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.system.api.domain.SysUser;
import com.things.cgomp.system.api.model.LoginUser;
import com.things.cgomp.system.constant.SecurityConstant;
import com.things.cgomp.system.domain.dto.DeleteDTO;
import com.things.cgomp.system.domain.dto.UpdatePwdDTO;
import com.things.cgomp.system.enums.ErrorCodeConstants;
import com.things.cgomp.system.service.*;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 用户信息
 * 
 * @author things
 */
@Log(title = "用户管理")
@RestController
@RequestMapping("/user")
public class SysUserController extends BaseController
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysOrgService orgService;

    @Autowired
    private ISysPermissionService permissionService;

    @Autowired
    private ISysConfigService configService;

    /**
     * 获取用户分页列表
     */
    @RequiresPermissions({
            "system:user:list",
            "system:user:platform",
            "system:user:operator",
    })
    @GetMapping("/page")
    public R<PageInfo<SysUser>> list(SysUser user)
    {
        startPage();
        PageInfo<SysUser> pageInfo = userService.selectUserPage(user);
        return R.ok(pageInfo);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.importTemplateExcel(response, "用户数据");
    }

    /**
     * 获取当前用户信息
     */
    @InnerAuth
    @GetMapping("/info/{username}")
    public R<LoginUser> info(@PathVariable("username") String username)
    {
        SysUser sysUser = userService.selectUserByUserName(username);
        if (StringUtils.isNull(sysUser))
        {
            return R.fail("用户名或密码错误");
        }
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(sysUser);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(sysUser);
        LoginUser sysUserVo = new LoginUser();
        sysUserVo.setSysUser(sysUser);
        sysUserVo.setRoles(roles);
        sysUserVo.setPermissions(permissions);
        return R.ok(sysUserVo);
    }

    /**
     * 注册用户信息
     */
    @InnerAuth
    @PostMapping("/register")
    public R<Boolean> register(@RequestBody SysUser sysUser)
    {
        String username = sysUser.getUsername();
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return R.fail("当前系统没有开启注册功能！");
        }
        if (!userService.checkUserNameUnique(sysUser))
        {
            return R.fail("保存用户'" + username + "'失败，注册账号已存在");
        }
        return R.ok(userService.registerUser(sysUser));
    }

    /**
     *记录用户登录IP地址和登录时间
     */
    @InnerAuth
    @PutMapping("/recordlogin")
    public R<Boolean> recordlogin(@RequestBody SysUser sysUser)
    {
        return R.ok(userService.updateUserProfile(sysUser));
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public R<?> getInfo()
    {
        SysUser user = userService.selectUserById(SecurityUtils.getUserId());
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        user.hidePwd();
        Map<String,Object> resp = new HashMap<>(3);
        resp.put("user", user);
        resp.put("roles", roles);
        resp.put("permissions", permissions);
        return R.ok(resp);
    }

    /**
     * 根据用户编号获取详细信息
     */
    @RequiresPermissions({
            "system:user:query",
            "system:user:platform:query",
            "system:user:operator:query"
    })
    @GetMapping(value = {"" })
    public R<SysUser> getInfo(SysUser req)
    {
        SysUser user = userService.getUserInfo(req.getUserId());
        return R.ok(user);
    }

    /**
     * 新增用户
     */
    @RequiresPermissions({
            "system:user:add",
            "system:user:platform:add",
            "system:user:operator:add"
    })
    @Log(method = "新增用户", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@Validated @RequestBody SysUser user)
    {
        if (ObjectUtil.isEmpty(user.getUsername())) {
            throw exception(ErrorCodeConstants.USER_USERNAME_NOT_NULL);
        }
        orgService.checkOrgDataScope(user.getOrgId());
        roleService.checkRoleDataScope(user.getRoleIds());
        if (!userService.checkUserNameUnique(user)) {
            throw exception(ErrorCodeConstants.USER_USERNAME_IS_EXIST, user.getUsername());
        }
        return R.ok(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @RequiresPermissions({
            "system:user:edit",
            "system:user:operator:edit",
            "system:user:platform:edit"
    })
    @Log(method = "修改用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@Validated @RequestBody SysUser user) {
        if (ObjectUtil.isEmpty(user.getUserId())) {
            throw exception(ErrorCodeConstants.USER_USER_ID_NOT_NULL);
        }
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        orgService.checkOrgDataScope(user.getOrgId());
        roleService.checkRoleDataScope(user.getRoleIds());
        if (!userService.checkUserNameUnique(user)) {
            throw exception(ErrorCodeConstants.USER_MOBILE_IS_EXIST, user.getUsername());
        }
        user.setUpdateBy(SecurityUtils.getUserId());
        return R.ok(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
    @RequiresPermissions({
            "system:user:remove",
            "system:user:platform:remove",
            "system:user:operator:remove"
    })
    @Log(method = "删除用户", businessType = BusinessType.DELETE)
    @DeleteMapping()
    public R<Integer> remove(@Validated @RequestBody DeleteDTO deleteDto)
    {
        if (ArrayUtils.contains(deleteDto.getIds(), SecurityUtils.getUserId()))
        {
            throw exception(ErrorCodeConstants.USER_CURRENT_NOT_DELETE);
        }
        return R.ok(userService.deleteUserByIds(deleteDto.getIds()));
    }

    /**
     * 重置密码
     */
    @RequiresPermissions({
            "system:user:edit",
            "system:user:operator:edit",
            "system:user:platform:edit"
    })
    @Log(method = "重置用户密码", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public R<Integer> resetPwd(@RequestBody SysUser user)
    {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        user.setPassword(SecurityUtils.encryptPassword(SecurityConstant.USER_DEFAULT_PASS));
        user.setUpdateBy(SecurityUtils.getUserId());;
        return R.ok(userService.resetPwd(user));
    }

    /**
     * 修改密码
     */
    @RequiresPermissions({
            "system:user:edit",
            "system:user:operator:edit",
            "system:user:platform:edit"
    })
    @Log(method = "用户修改密码", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public R<Integer> updatePwd(@RequestBody UpdatePwdDTO user)
    {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        return R.ok(userService.updatePwd(user));
    }
}
