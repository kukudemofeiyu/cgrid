package com.things.cgomp.system.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.api.domain.AppUserBlacklist;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.log.enums.BusinessType;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.system.enums.ErrorCodeConstants;
import com.things.cgomp.system.service.IAppUserBlacklistService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 注册用户黑名单管理
 *
 * @author things
 */
@Log(title = "注册用户黑名单管理")
@RestController
@RequestMapping("/register/blacklist")
public class AppUserBlacklistController {

    @Resource
    private IAppUserBlacklistService blacklistService;


    /**
     * 分页查询注册用户黑名单
     *
     * @param blacklist 请求对象
     * @return PageInfo
     */
    @GetMapping("/page")
    @RequiresPermissions("app:register:blacklist:list")
    public R<PageInfo<AppUserBlacklist>> page(AppUserBlacklist blacklist) {
        PageInfo<AppUserBlacklist> pageInfo = blacklistService.selectPage(blacklist);
        return R.ok(pageInfo);
    }

    /**
     * 查询用户黑名单信息
     *
     * @return AppUserBlacklist
     */
    @GetMapping("")
    @RequiresPermissions("app:register:blacklist:query")
    public R<AppUserBlacklist> getInfo(AppUserBlacklist blacklist) {
        return R.ok(blacklistService.selectBlackListById(blacklist.getId()));
    }

    /**
     * 新增注册用户黑名单
     *
     * @return Integer
     */
    @Log(method = "新增黑名单", businessType = BusinessType.INSERT)
    @PostMapping("")
    @RequiresPermissions("app:register:blacklist:add")
    public R<Integer> add(@Validated @RequestBody AppUserBlacklist blacklist) {
        return R.ok(blacklistService.saveBlacklist(blacklist));
    }

    /**
     * 编辑注册用户黑名单
     *
     * @return Integer
     */
    @Log(method = "编辑黑名单", businessType = BusinessType.UPDATE)
    @PutMapping("")
    @RequiresPermissions("app:register:blacklist:edit")
    public R<Integer> edit(@Validated @RequestBody AppUserBlacklist blacklist) {
        if (blacklist.getId() == null) {
            throw exception(ErrorCodeConstants.APP_USER_BLACKLIST_ID_IS_NULL);
        }
        return R.ok(blacklistService.updateBlacklist(blacklist));
    }

    /**
     * 删除黑名单
     *
     * @return Integer
     */
    @Log(method = "删除黑名单", businessType = BusinessType.DELETE)
    @DeleteMapping("")
    @RequiresPermissions("app:register:blacklist:remove")
    public R<Integer> delete(@RequestBody AppUserBlacklist blacklist) {
        if (blacklist.getId() == null) {
            throw exception(ErrorCodeConstants.APP_USER_BLACKLIST_ID_IS_NULL);
        }
        boolean isDelete = blacklistService.removeById(blacklist.getId());
        return R.ok(isDelete ? 1 : 0);
    }
}
