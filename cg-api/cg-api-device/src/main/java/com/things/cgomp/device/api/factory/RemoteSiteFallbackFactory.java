package com.things.cgomp.device.api.factory;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.device.api.RemoteSiteService;
import com.things.cgomp.device.api.domain.Site;
import com.things.cgomp.device.api.model.vo.SiteAppVO;
import com.things.cgomp.device.api.model.vo.SiteAppInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RemoteSiteFallbackFactory implements FallbackFactory<RemoteSiteService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteSiteFallbackFactory.class);
    @Override
    public RemoteSiteService create(Throwable throwable) {
        log.error("设备站点服务调用失败:{}", throwable.getMessage());
        return new RemoteSiteService() {
            @Override
            public R<Site> selectSite(Long id) {
                return R.fail("远程服务调用失败: " + throwable.getMessage());
            }

            @Override
            public R<List<SiteAppVO>> selectSiteList(Integer deviceType, String city, String siteName, String address, Integer recommendSitesStatus, Integer pileType, List<String> supports, Integer parkCarStatus, Integer receiptStatus, Float lat, Float lng, String source) {
                // 回退逻辑，返回默认值或空列表
                return R.fail("远程服务调用失败: " + throwable.getMessage());
            }

            @Override
            public R<SiteAppInfoVO> selectSiteAppInfo(Long siteId, Float lat, Float lng, String source) {
                return R.fail("远程服务调用失败: " + throwable.getMessage());
            }

            @Override
            public R<Map<Long, Site>> selectSiteMap(List<Long> ids) {
                return R.fail(
                        new HashMap<>(),
                        throwable.getMessage()
                );
            }

            @Override
            public R<Long> getOperatorId(Long siteId) {
                return R.fail(throwable.getMessage());
            }
        };
    }
}
