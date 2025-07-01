package com.things.cgomp.pay.api.factory;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.pay.api.RemoteSiteDiscountTemplateService;
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
public class RemoteSiteDiscountTemplateFallbackFactory implements FallbackFactory<RemoteSiteDiscountTemplateService> {

    @Override
    public RemoteSiteDiscountTemplateService create(Throwable throwable) {
        log.error("站点活动模板服务调用失败:{}", throwable.getMessage());
        return new RemoteSiteDiscountTemplateService() {

            @Override
            public R<Map<Long, String>> getTemplateNameMap(List<Long> ids) {
                return R.fail(
                        new HashMap<>(),
                        throwable.getMessage()
                );
            }
        };
    }
}
