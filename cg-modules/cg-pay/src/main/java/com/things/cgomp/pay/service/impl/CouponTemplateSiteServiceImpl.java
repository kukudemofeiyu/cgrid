package com.things.cgomp.pay.service.impl;

import com.things.cgomp.pay.domain.CouponTemplateSite;
import com.things.cgomp.pay.mapper.CouponTemplateSiteMapper;
import com.things.cgomp.pay.service.ICouponTemplateSiteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 优惠券模板站点表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-25
 */
@Service
public class CouponTemplateSiteServiceImpl extends ServiceImpl<CouponTemplateSiteMapper, CouponTemplateSite>
        implements ICouponTemplateSiteService {

    @Override
    public void saveSiteIds(
            Long templateId,
            List<Long> siteIds
    ) {
        if(CollectionUtils.isEmpty(siteIds)){
            return;
        }

        siteIds.stream()
                .map(siteId -> new CouponTemplateSite(
                        templateId,
                        siteId
                ))
                .forEach(baseMapper::insert);
    }

    @Override
    public List<Long> getSiteIds(
            Long templateId
    ) {
        return baseMapper.getSiteIds(
                templateId
        );
    }

    @Override
    public Map<Long, List<Long>> getSiteIdsMap(
            List<Long> templateIds
    ) {
        if(CollectionUtils.isEmpty(templateIds)){
            return new HashMap<>();
        }

        List<CouponTemplateSite> templateSites = baseMapper.getTemplateSites(templateIds);

        Map<Long, List<Long>> siteIdsMap = new HashMap<>();
        for (CouponTemplateSite templateSite : templateSites) {
            List<Long> siteIds = siteIdsMap
                    .computeIfAbsent(templateSite.getTemplateId(), k -> new ArrayList<>());
            siteIds.add(templateSite.getSiteId());
        }

        return siteIdsMap;
    }
}
