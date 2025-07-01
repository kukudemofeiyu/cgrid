package com.things.cgomp.system.api;

import com.things.cgomp.app.api.domain.AppUser;
import com.things.cgomp.app.api.domain.AppUserCar;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.system.api.dto.AppUserTrendDataDTO;
import com.things.cgomp.system.api.factory.RemoteAppUserFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(
        contextId = "remoteAppUserService",
        value = ServiceNameConstants.SYSTEM_SERVICE,
//        url = "http://localhost:9012",
        fallbackFactory = RemoteAppUserFallbackFactory.class
)
public interface RemoteAppUserService
{

    @GetMapping(value = "/register",name = "查询用户信息")
    R<AppUser> getUserInfo(
            @RequestParam("userId") Long userId
    );

    @GetMapping(value = "/register/car/default", name = "查询默认车辆")
    R<AppUserCar> selectDefaultCar(
            @RequestParam("userId") Long userId
    );

    @GetMapping(value = "/register/getTotalTrend", name = "获取趋势数据")
    R<AppUserTrendDataDTO> getTotalTrend(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @PutMapping(value = "/register/firstChargeStatus", name = "更新用户首次充电状态")
    R<?> updateFirstChargeStatus(
            @RequestParam("userId") Long userId
    );

    @GetMapping(value = "/register/firstChargeStatus", name = "查询用户首次充电状态")
    R<Integer> selectFirstChargeStatus(
            @RequestParam("userId") Long userId
    );
}
