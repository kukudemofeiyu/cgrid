package com.things.cgomp.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.pay.api.domain.SiteDiscountTemplate;
import com.things.cgomp.pay.dto.sitediscount.SiteDiscountTemplatePageDTO;
import com.things.cgomp.pay.vo.sitediscount.SiteDiscountTemplateVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 站点折扣模板表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
public interface ISiteDiscountTemplateService extends IService<SiteDiscountTemplate> {

    Map<Long, String> getTemplateNameMap(
            List<Long> ids
    );

    void checkTemplates(
            List<Long> templateIds
    );

    Long saveTemplate(
            SiteDiscountTemplate siteDiscountTemplate
    );

    SiteDiscountTemplate getTemplate(
            Long id
    );

    void editTemplate(
            SiteDiscountTemplate siteDiscountTemplate
    );

    void deleteTemplate(
          Long id
    );

    PageInfo<SiteDiscountTemplateVo> selectPage(
            SiteDiscountTemplatePageDTO pageDTO
    );

    List<SiteDiscountTemplateVo> selectTemplates(
            SiteDiscountTemplatePageDTO pageDTO
    );

    Map<Long,SiteDiscountTemplate> getTemplateMap(
            List<Long> ids
    );

}
