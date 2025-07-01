package com.things.cgomp.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.pay.domain.CouponActivityTemplate;
import com.things.cgomp.pay.mapper.CouponActivityTemplateMapper;
import com.things.cgomp.pay.service.ICouponActivityTemplateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 优惠券活动模板表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-04-09
 */
@Service
public class CouponActivityTemplateServiceImpl extends ServiceImpl<CouponActivityTemplateMapper, CouponActivityTemplate>
        implements ICouponActivityTemplateService {

    @Override
    public void saveTemplateIds(Long activityId, List<Long> templateIds) {
        if(templateIds.isEmpty()){
            return;
        }

        templateIds.stream()
                .map(userId -> new CouponActivityTemplate(
                        activityId,
                        userId
                ))
                .forEach(baseMapper::insert);
    }

    @Override
    public void updateTemplateIds(Long activityId, List<Long> templateIds) {
        baseMapper.deleteTemplateIds(activityId);

        saveTemplateIds(
                activityId,
                templateIds
        );
    }
}
