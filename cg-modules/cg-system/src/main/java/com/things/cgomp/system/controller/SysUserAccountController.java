package com.things.cgomp.system.controller;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.security.annotation.InnerAuth;
import com.things.cgomp.system.api.domain.SysUserAccount;
import com.things.cgomp.system.enums.ErrorCodeConstants;
import com.things.cgomp.system.service.ISysUserAccountService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 用户账号信息
 *
 * @author things
 */
@Log(title = "用户账号管理")
@RestController
@RequestMapping("/userAccount")
public class SysUserAccountController {

    @Resource
    private ISysUserAccountService userAccountService;

    /**
     * 内部接口
     * 获取用户账号信息
     *
     * @return 账号信息
     */
    @InnerAuth
    @GetMapping("info")
    public R<SysUserAccount> getInfo(@RequestParam("userId") Long userId, @RequestParam("type") String accountType) {
        SysUserAccount account = userAccountService.selectByUserIdAndType(userId, accountType);
        return R.ok(account);
    }

    /**
     * 内部接口
     * 保存用户账号
     *
     * @return Integer
     */
    @InnerAuth
    @PostMapping("")
    public R<Integer> add(@Validated @RequestBody SysUserAccount account) {
        if (!userAccountService.checkUserAccountUnique(account.getUserId(), account.getType())) {
            throw exception(ErrorCodeConstants.USER_ACCOUNT_IS_EXIST);
        }
        return R.ok(userAccountService.saveUserAccount(account));
    }
}
