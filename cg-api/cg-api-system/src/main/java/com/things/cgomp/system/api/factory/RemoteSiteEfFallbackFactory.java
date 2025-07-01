package com.things.cgomp.system.api.factory;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.system.api.RemoteSiteEfService;
import com.things.cgomp.system.api.domain.SysSiteEf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 站点关联服务降级处理
 * 
 * @author things
 */
@Slf4j
@Component
public class RemoteSiteEfFallbackFactory implements FallbackFactory<RemoteSiteEfService>
{

    @Override
    public RemoteSiteEfService create(Throwable throwable)
    {
        log.error("站点关联服务调用失败:{}", throwable.getMessage());
        return new RemoteSiteEfService() {
            @Override
            public R<List<Long>> getAllSiteIdsByUserId(Long userId, String source) {
                return R.fail("获取用户站点失败:" + throwable.getMessage());
            }

            @Override
            public R<Integer> addSiteEf(SysSiteEf sysSiteEf, String source) {
                return R.fail("新增站点关联失败:" + throwable.getMessage());
            }

            @Override
            public R<Integer> updateSiteEf(SysSiteEf sysSiteEf, String source) {
                return R.fail("修改站点关联失败:" + throwable.getMessage());
            }

            @Override
            public R<Integer> deleteSiteEf(Long siteId, String source) {
                return R.fail("删除站点关联失败:" + throwable.getMessage());
            }
        };
    }
}
