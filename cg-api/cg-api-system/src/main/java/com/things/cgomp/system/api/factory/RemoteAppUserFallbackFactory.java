package com.things.cgomp.system.api.factory;

import com.things.cgomp.app.api.domain.AppUser;
import com.things.cgomp.app.api.domain.AppUserCar;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.system.api.RemoteAppUserService;
import com.things.cgomp.system.api.dto.AppUserTrendDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class RemoteAppUserFallbackFactory implements FallbackFactory<RemoteAppUserService> {

    @Override
    public RemoteAppUserService create(Throwable throwable)
    {
        log.error("app用户服务调用失败:{}", throwable.getMessage());
        return new RemoteAppUserService()
        {
            @Override
            public R<AppUser> getUserInfo(Long userId) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<AppUserCar> selectDefaultCar(Long userId) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<AppUserTrendDataDTO> getTotalTrend(String source) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<?> updateFirstChargeStatus(Long userId) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<Integer> selectFirstChargeStatus(Long userId) {
                return R.fail(throwable.getMessage());
            }
        };
    }
}
