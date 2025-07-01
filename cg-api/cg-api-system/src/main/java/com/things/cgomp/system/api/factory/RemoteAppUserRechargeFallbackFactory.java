package com.things.cgomp.system.api.factory;

import com.things.cgomp.app.api.domain.AppUser;
import com.things.cgomp.app.api.domain.AppUserCar;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.system.api.RemoteAppUserRechargeService;
import com.things.cgomp.system.api.RemoteAppUserService;
import com.things.cgomp.system.api.dto.AppRechargeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class RemoteAppUserRechargeFallbackFactory implements FallbackFactory<RemoteAppUserRechargeService> {

    @Override
    public RemoteAppUserRechargeService create(Throwable throwable)
    {
        log.error("app用户注册服务调用失败:{}", throwable.getMessage());
        return new RemoteAppUserRechargeService()
        {
            @Override
            public R<Integer> appRecharge(AppRechargeDTO req, String source) {
                return R.fail("app用户充值失败:" + throwable.getMessage());
            }
        };
    }
}
