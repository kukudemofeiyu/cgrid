package com.things.cgomp.system.api;

import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.system.api.domain.SysUserAccount;
import com.things.cgomp.system.api.factory.RemoteUserAccountFallbackFactory;
import com.things.cgomp.system.api.factory.RemoteUserFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 用户账户服务
 * 
 * @author things
 */
@FeignClient(contextId = "remoteUserAccountService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteUserAccountFallbackFactory.class)
public interface RemoteUserAccountService
{
    /**
     * 通过用户ID和用户类型查询账号信息
     *
     * @param userId 用户ID
     * @param accountType 用户类型
     *        @see com.things.cgomp.common.core.enums.UserAccountType
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/userAccount/info")
    R<SysUserAccount> getUserInfo(@RequestParam("userId") Long userId, @RequestParam("type") String accountType,
                                  @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 新增用户账号
     *
     * @param userAccount 账号实体
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("/userAccount")
    R<Boolean> saveAccount(@RequestBody SysUserAccount userAccount, @RequestHeader(SecurityConstants.FROM_SOURCE) String source) throws Exception;
}
