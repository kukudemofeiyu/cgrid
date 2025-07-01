package com.things.cgomp.pay.service.impl;

import com.things.cgomp.pay.domain.SiteDiscountActivitySite;
import com.things.cgomp.pay.mapper.SiteDiscountActivitySiteMapper;
import com.things.cgomp.pay.service.ISiteDiscountActivitySiteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 站点活动站点表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
@Service
public class SiteDiscountActivitySiteServiceImpl extends ServiceImpl<SiteDiscountActivitySiteMapper, SiteDiscountActivitySite>
        implements ISiteDiscountActivitySiteService {

    @Override
    public void saveSiteIds(Long activityId, List<Long> siteIds) {
        if(CollectionUtils.isEmpty(siteIds)){
            return;
        }

        siteIds.stream()
                .map(siteId -> new SiteDiscountActivitySite(
                        activityId,
                        siteId
                ))
                .forEach(baseMapper::insert);
    }

    @Override
    public void updateSiteIds(
            Long activityId,
            List<Long> siteIds
    ) {
        baseMapper.deleteByActivityId(activityId);

        saveSiteIds(
                activityId,
                siteIds
        );
    }

    @Override
    public List<Long> getSiteIds(Long activityId) {
        return baseMapper.getSiteIds(activityId);
    }

    @Override
    public Map<Long, List<Long>> getSiteIdsMap(List<Long> activityIds) {
        if (CollectionUtils.isEmpty(activityIds)) {
            return new HashMap<>();
        }

        List<SiteDiscountActivitySite> activitySites = baseMapper.getActivitySites(activityIds);

        Map<Long, List<Long>> siteIdsMap = new HashMap<>();
        for (SiteDiscountActivitySite activitySite : activitySites) {
            List<Long> siteIds = siteIdsMap.computeIfAbsent(activitySite.getActivityId(), k -> new ArrayList<>());
            siteIds.add(activitySite.getSiteId());
        }

        return siteIdsMap;
    }
}
