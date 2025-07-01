package com.things.cgomp.pay.service;

import com.things.cgomp.pay.domain.SiteDiscountActivitySite;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 站点活动站点表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
public interface ISiteDiscountActivitySiteService extends IService<SiteDiscountActivitySite> {

    void saveSiteIds(
            Long activityId,
            List<Long> siteIds
    );

    void updateSiteIds(
            Long activityId,
            List<Long> siteIds
    );

    List<Long> getSiteIds(
            Long activityId
    );

    Map<Long,List<Long>> getSiteIdsMap(
            List<Long> activityIds
    );
}
