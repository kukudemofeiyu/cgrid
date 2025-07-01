package com.things.cgomp.pay.service;

import com.things.cgomp.pay.domain.CouponActivityTemplate;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 优惠券活动模板表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-04-09
 */
public interface ICouponActivityTemplateService extends IService<CouponActivityTemplate> {

    void saveTemplateIds(
            Long activityId,
            List<Long> templateIds
    );

    void updateTemplateIds(
            Long activityId,
            List<Long> templateIds
    );

}
