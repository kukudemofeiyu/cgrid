package com.things.cgomp.pay.api.factory;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.pay.api.RemoteSiteDiscountActivityService;
import com.things.cgomp.pay.api.domain.SiteDiscountActivity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 计费策略服务降级处理
 *
 * @author things
 */
@Slf4j
@Component
public class RemoteSiteDiscountActivityFallbackFactory implements FallbackFactory<RemoteSiteDiscountActivityService> {

    @Override
    public RemoteSiteDiscountActivityService create(Throwable throwable) {
        log.error("站点活动服务调用失败:{}", throwable.getMessage());
        return new RemoteSiteDiscountActivityService() {
            @Override
            public R<SiteDiscountActivity> getActivity(Long id) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<Map<Long, String>> getActivityNameMap(List<Long> ids) {
                return R.fail(
                        new HashMap<>(),
                        throwable.getMessage()
                );
            }
        };
    }
}
