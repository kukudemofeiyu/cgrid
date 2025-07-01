package com.things.cgomp.order.api;

import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.order.api.factory.RemoteShareholdersFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 分成者服务
 *
 * @author things
 */
@FeignClient(contextId = "remoteShareholdersService", value = ServiceNameConstants.ORDER_SERVICE, fallbackFactory = RemoteShareholdersFallbackFactory.class)
public interface RemoteShareholdersService {

}
