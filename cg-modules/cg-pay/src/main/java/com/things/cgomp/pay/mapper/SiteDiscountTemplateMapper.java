package com.things.cgomp.pay.mapper;

import com.things.cgomp.pay.api.domain.SiteDiscountTemplate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.pay.dto.sitediscount.SiteDiscountTemplatePageDTO;
import com.things.cgomp.pay.vo.sitediscount.SiteDiscountTemplateVo;

import java.util.List;

/**
 * <p>
 * 站点折扣模板表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
public interface SiteDiscountTemplateMapper extends BaseMapper<SiteDiscountTemplate> {

    List<SiteDiscountTemplateVo> selectTemplates(
            SiteDiscountTemplatePageDTO pageDTO
    );
}
