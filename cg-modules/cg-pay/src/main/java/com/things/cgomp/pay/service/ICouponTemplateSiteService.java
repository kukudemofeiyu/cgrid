package com.things.cgomp.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.things.cgomp.pay.domain.CouponTemplateSite;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 优惠券模板站点表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-25
 */
public interface ICouponTemplateSiteService extends IService<CouponTemplateSite> {

    void saveSiteIds(
            Long templateId,
            List<Long> siteIds
    );

    List<Long> getSiteIds(
            Long templateId
    );

    Map<Long, List<Long>> getSiteIdsMap(
            List<Long> templateIds
    );
}
