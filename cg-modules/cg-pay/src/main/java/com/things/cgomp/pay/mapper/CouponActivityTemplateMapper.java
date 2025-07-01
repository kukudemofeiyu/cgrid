package com.things.cgomp.pay.mapper;

import com.things.cgomp.pay.domain.CouponActivityTemplate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 优惠券活动模板表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-04-09
 */
public interface CouponActivityTemplateMapper extends BaseMapper<CouponActivityTemplate> {

    Integer deleteTemplateIds(
            @Param("activityId") Long activityId
    );

}
