package com.things.cgomp.pay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.pay.api.domain.SiteDiscountActivity;
import com.things.cgomp.pay.dto.sitediscount.SiteDiscountActivityPageDTO;
import com.things.cgomp.pay.vo.sitediscount.SiteDiscountActivityVo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * <p>
 * 站点折扣活动表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
public interface SiteDiscountActivityMapper extends BaseMapper<SiteDiscountActivity> {

    List<SiteDiscountActivity> selectActivitiesByTemplateId(
            @Param("templateId") Long templateId
    );

    List<SiteDiscountActivityVo> selectActivities(
            SiteDiscountActivityPageDTO pageDTO
    );

    List<SiteDiscountActivity> selectSiteActivities(
            @Param("operatorId") Long operatorId,
            @Param("siteDimension") Integer siteDimension,
            @Param("startTime") LocalDate startTime,
            @Param("endTime") LocalDate endTime,
            @Param("siteIds") List<Long> siteIds
    );

    List<SiteDiscountActivity> selectAvailableActivities(
            @Param("operatorId") Long operatorId,
            @Param("date") LocalDate date,
            @Param("time") LocalTime time,
            @Param("siteId") Long siteId
    );

}
