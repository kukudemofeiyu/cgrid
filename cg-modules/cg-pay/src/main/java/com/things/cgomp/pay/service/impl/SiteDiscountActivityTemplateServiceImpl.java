package com.things.cgomp.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.pay.domain.SiteDiscountActivityTemplate;
import com.things.cgomp.pay.api.domain.SiteDiscountTemplate;
import com.things.cgomp.pay.mapper.SiteDiscountActivityTemplateMapper;
import com.things.cgomp.pay.service.ISiteDiscountActivityTemplateService;
import com.things.cgomp.pay.service.ISiteDiscountTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * 站点活动模板表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
@Service
public class SiteDiscountActivityTemplateServiceImpl
        extends ServiceImpl<SiteDiscountActivityTemplateMapper, SiteDiscountActivityTemplate>
        implements ISiteDiscountActivityTemplateService {

    @Resource
    private ISiteDiscountTemplateService siteDiscountTemplateService;

    @Override
    public void saveTemplateIds(
            Long activityId,
            List<Long> templateIds
    ) {
        if(CollectionUtils.isEmpty(templateIds)){
            return;
        }

        templateIds.stream()
                .map(templateId -> new SiteDiscountActivityTemplate(
                        activityId,
                        templateId
                ))
                .forEach(baseMapper::insert);
    }

    @Override
    public void updateTemplateIds(
            Long activityId,
            List<Long> templateIds
    ) {
        baseMapper.deleteByActivityId(activityId);
        saveTemplateIds(
                activityId,
                templateIds
        );
    }

    @Override
    public List<Long> getTemplateIds(
            Long activityId
    ) {
        return baseMapper.getTemplateIds(
                activityId
        );
    }

    @Override
    public List<SiteDiscountTemplate> getTemplates(
            Long activityId
    ) {
        List<Long> templateIds = baseMapper.getTemplateIds(
                activityId
        );

        if(templateIds.isEmpty()){
            return new ArrayList<>();
        }

        List<SiteDiscountTemplate> templates = siteDiscountTemplateService.listByIds(templateIds);
        templates.sort(Comparator.comparing(SiteDiscountTemplate::getStartTime));
        return templates;
    }
}
