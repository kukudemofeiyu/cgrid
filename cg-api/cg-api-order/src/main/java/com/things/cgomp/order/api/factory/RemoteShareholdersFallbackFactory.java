package com.things.cgomp.order.api.factory;

import com.things.cgomp.order.api.RemoteShareholdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 分成者服务降级处理
 *
 * @author things
 */
@Slf4j
@Component
public class RemoteShareholdersFallbackFactory implements FallbackFactory<RemoteShareholdersService> {

    @Override
    public RemoteShareholdersService create(Throwable throwable) {
        log.error("订单服务调用失败:{}", throwable.getMessage());
        return new RemoteShareholdersService() {

        };
    }
}
