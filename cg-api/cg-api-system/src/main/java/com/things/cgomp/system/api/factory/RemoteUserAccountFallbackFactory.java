package com.things.cgomp.system.api.factory;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.system.api.RemoteUserAccountService;
import com.things.cgomp.system.api.domain.SysUserAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 用户账户服务降级处理
 * 
 * @author things
 */
@Slf4j
@Component
public class RemoteUserAccountFallbackFactory implements FallbackFactory<RemoteUserAccountService>
{

    @Override
    public RemoteUserAccountService create(Throwable throwable)
    {
        log.error("用户账户服务调用失败:{}", throwable.getMessage());
        return new RemoteUserAccountService() {
            @Override
            public R<SysUserAccount> getUserInfo(Long userId, String accountType, String source) {
                return R.fail("获取用户账号信息失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> saveAccount(SysUserAccount userAccount, String source) throws Exception {
                return R.fail("保存用户账号信息失败:" + throwable.getMessage());
            }
        };
    }
}
