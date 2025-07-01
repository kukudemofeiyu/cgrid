package com.things.cgomp.system.api;

import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.system.api.dto.AppRechargeDTO;
import com.things.cgomp.system.api.factory.RemoteAppUserRechargeFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "remoteAppUserRechargeService", value = ServiceNameConstants.SYSTEM_SERVICE,
                url = "http://localhost:9012",
        fallbackFactory = RemoteAppUserRechargeFallbackFactory.class)
public interface RemoteAppUserRechargeService {
    @PostMapping("/register/appRecharge")
    R<Integer> appRecharge(@RequestBody AppRechargeDTO req, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}

