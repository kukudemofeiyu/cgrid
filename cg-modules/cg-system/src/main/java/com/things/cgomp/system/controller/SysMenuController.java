package com.things.cgomp.system.controller;

import com.things.cgomp.common.core.constant.UserConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.core.web.controller.BaseController;
import com.things.cgomp.common.core.web.domain.AjaxResult;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.log.enums.BusinessType;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.system.api.domain.SysRole;
import com.things.cgomp.system.domain.SysMenu;
import com.things.cgomp.system.domain.vo.RoleMenuVO;
import com.things.cgomp.system.domain.vo.RouterVo;
import com.things.cgomp.system.domain.vo.TreeSelect;
import com.things.cgomp.system.enums.ErrorCodeConstants;
import com.things.cgomp.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 菜单信息
 * 
 * @author things
 */
@Log(title = "菜单管理")
@RestController
@RequestMapping("/menu")
public class SysMenuController extends BaseController
{
    @Autowired
    private ISysMenuService menuService;

    /**
     * 获取菜单列表
     */
    @RequiresPermissions("system:menu:list")
    @GetMapping("/list")
    public AjaxResult list(SysMenu menu)
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuList(menu, userId);
        return success(menuService.buildMenuTree(menus));
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @RequiresPermissions("system:menu:query")
    @GetMapping(value = "")
    public R<SysMenu> getInfo(SysMenu req)
    {
        return R.ok(menuService.selectMenuById(req.getMenuId()));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/simple-list")
    public AjaxResult simpleList(SysMenu menu)
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuList(menu, userId);
        return success(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeSelect")
    public R<RoleMenuVO> roleMenuTreeSelect(SysRole req)
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuList(userId);
        List<Long> checkedKeys = menuService.selectMenuListByRoleId(req.getRoleId());
        List<TreeSelect> trees = menuService.buildMenuTreeSelect(menus);
        RoleMenuVO resp = RoleMenuVO.builder().checkedKeys(checkedKeys).menus(trees).build();
        return R.ok(resp);
    }

    /**
     * 新增菜单
     */
    @RequiresPermissions("system:menu:add")
    @Log(method = "新增菜单", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@Validated @RequestBody SysMenu menu) {
        if (!menuService.checkMenuNameUnique(menu)) {
            throw exception(ErrorCodeConstants.MENU_NAME_IS_EXIST);
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            throw exception(ErrorCodeConstants.MENU_FRAME_NEED_HTTPS);
        }
        menu.setCreateBy(SecurityUtils.getUserId());
        return R.ok(menuService.insertMenu(menu));
    }

    /**
     * 修改菜单
     */
    @RequiresPermissions("system:menu:edit")
    @Log(method = "修改菜单", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@Validated @RequestBody SysMenu menu) {
        if (!menuService.checkMenuNameUnique(menu)) {
            throw exception(ErrorCodeConstants.MENU_NAME_IS_EXIST);
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            throw exception(ErrorCodeConstants.MENU_FRAME_NEED_HTTPS);
        } else if (menu.getMenuId().equals(menu.getParentId())) {
            throw exception(ErrorCodeConstants.MENU_PARENT_CAN_NOT_SELF);
        }
        menu.setUpdateBy(SecurityUtils.getUserId());
        return R.ok(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     */
    @RequiresPermissions("system:menu:remove")
    @Log(method = "删除菜单", businessType = BusinessType.DELETE)
    @DeleteMapping("")
    public R<Integer> remove(@RequestBody SysMenu req) {
        if (req.getMenuId() == null) {
            throw exception(ErrorCodeConstants.MENU_ID_IS_NOT_NULL);
        }
        if (menuService.hasChildByMenuId(req.getMenuId())) {
            throw exception(ErrorCodeConstants.MENU_HAS_CHILD_CAN_NOT_DELETE);
        }
        if (menuService.checkMenuExistRole(req.getMenuId())) {
            throw exception(ErrorCodeConstants.MENU_HAS_ASSIGN_CAN_NOT_DELETE);
        }
        return R.ok(menuService.deleteMenuById(req.getMenuId()));
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public R<List<RouterVo>> getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return R.ok(menuService.buildMenus(menus));
    }
}