package com.things.cgomp.pay.mapper;

import com.things.cgomp.pay.domain.CouponTemplateSite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 优惠券模板站点表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-03-25
 */
public interface CouponTemplateSiteMapper extends BaseMapper<CouponTemplateSite> {

    List<Long> getSiteIds(
          @Param("templateId") Long templateId
    );

    List<CouponTemplateSite> getTemplateSites(
            @Param("templateIds") List<Long> templateIds
    );
}
