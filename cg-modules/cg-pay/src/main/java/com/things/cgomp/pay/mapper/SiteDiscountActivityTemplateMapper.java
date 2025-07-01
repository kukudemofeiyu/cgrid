package com.things.cgomp.pay.mapper;

import com.things.cgomp.pay.domain.SiteDiscountActivityTemplate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 站点活动模板表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
public interface SiteDiscountActivityTemplateMapper extends BaseMapper<SiteDiscountActivityTemplate> {

    List<Long> getTemplateIds(
            @Param("activityId") Long activityId
    );

    Long deleteByActivityId(
            @Param("activityId") Long activityId
    );
}
