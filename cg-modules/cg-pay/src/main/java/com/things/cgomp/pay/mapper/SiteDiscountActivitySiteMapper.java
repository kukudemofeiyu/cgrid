package com.things.cgomp.pay.mapper;

import com.things.cgomp.pay.domain.SiteDiscountActivitySite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 站点活动站点表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
public interface SiteDiscountActivitySiteMapper extends BaseMapper<SiteDiscountActivitySite> {

    List<Long> getSiteIds(
          @Param("activityId") Long activityId
    );

    Long deleteByActivityId(
            @Param("activityId") Long activityId
    );

    List<SiteDiscountActivitySite> getActivitySites(
            @Param("activityIds") List<Long> activityIds
    );
}
