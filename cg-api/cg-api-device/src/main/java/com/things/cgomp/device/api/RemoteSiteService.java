package com.things.cgomp.device.api;

import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.device.api.domain.Site;
import com.things.cgomp.device.api.factory.RemoteSiteFallbackFactory;
import com.things.cgomp.device.api.model.vo.SiteAppVO;
import com.things.cgomp.device.api.model.vo.SiteAppInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(contextId = "remoteSiteService",
        value = ServiceNameConstants.SYSTEM_DEVICE,
        url = "http://localhost:9011",
        fallbackFactory = RemoteSiteFallbackFactory.class)
public interface RemoteSiteService {
    @GetMapping(value = "site", name = "查询站点信息")
    public R<Site> selectSite(
            @RequestParam(value = "id", required = false) Long id);
    @GetMapping(value = "site/appList", name = "站点信息列表")
    R<List<SiteAppVO>> selectSiteList(
            @RequestParam(value = "deviceType", required = false) Integer deviceType,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "siteName", required = false) String siteName,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "recommendSitesStatus", required = false) Integer recommendSitesStatus,
            @RequestParam(value = "pileType", required = false) Integer pileType,
            @RequestParam(value = "supports", required = false) List<String> supports,
            @RequestParam(value = "parkCarStatus", required = false) Integer parkCarStatus,
            @RequestParam(value = "receiptStatus", required = false) Integer receiptStatus,
            @RequestParam(value = "lat", required = false) Float lat,
            @RequestParam(value = "lng", required = false) Float lng,
            @RequestHeader(SecurityConstants.FROM_SOURCE) String source
    );
    @GetMapping(value = "site/appInfo", name = "站点信息详情")
    R<SiteAppInfoVO> selectSiteAppInfo(
            @RequestParam(value = "siteId", required = false) Long siteId,
            @RequestParam(value = "lat", required = false) Float lat,
            @RequestParam(value = "lng", required = false) Float lng,
            @RequestHeader(SecurityConstants.FROM_SOURCE) String source
    );

    @GetMapping(value = "site/siteMap", name = "查询站点Map")
    R<Map<Long,Site>> selectSiteMap(
            @RequestParam(value = "ids", required = false) List<Long> ids
    );

    @GetMapping(value = "site/operatorId", name = "查询运营商id")
    R<Long> getOperatorId(
            @RequestParam(value = "id") Long id
    );
}
