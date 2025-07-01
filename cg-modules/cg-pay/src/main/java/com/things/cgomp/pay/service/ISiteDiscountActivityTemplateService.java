package com.things.cgomp.pay.service;

import com.things.cgomp.pay.domain.SiteDiscountActivityTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.things.cgomp.pay.api.domain.SiteDiscountTemplate;

import java.util.List;

/**
 * <p>
 * 站点活动模板表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
public interface ISiteDiscountActivityTemplateService extends IService<SiteDiscountActivityTemplate> {

    void saveTemplateIds(
            Long activityId,
            List<Long> templateIds
    );

    void updateTemplateIds(
            Long activityId,
            List<Long> templateIds
    );

    List<Long> getTemplateIds(
            Long activityId
    );

    List<SiteDiscountTemplate> getTemplates(
            Long activityId
    );
}
