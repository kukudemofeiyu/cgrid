package com.things.cgomp.pay.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.pay.api.domain.SiteDiscountTemplate;
import com.things.cgomp.pay.dto.sitediscount.SiteDiscountTemplatePageDTO;
import com.things.cgomp.pay.service.ISiteDiscountTemplateService;
import com.things.cgomp.pay.vo.sitediscount.SiteDiscountTemplateVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 站点折扣模板表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
@RestController
@RequestMapping(value = "/siteDiscountTemplate",name = "站点折扣模板")
public class SiteDiscountTemplateController {

    @Resource
    private ISiteDiscountTemplateService siteDiscountTemplateService;

    @PostMapping(value = "", name = "新增站点折扣模板")
    public R<Long> saveTemplate(
            @RequestBody SiteDiscountTemplate siteDiscountTemplate
    ) {
        Long templateId = siteDiscountTemplateService.saveTemplate(
                siteDiscountTemplate
        );
        return R.ok(templateId);
    }

    @GetMapping(value = "nameMap", name = "查询站点活动模板名称Map")
    R<Map<Long,String>> getTemplateNameMap(
            @RequestParam("ids") List<Long> ids
    ){
        Map<Long,String> templateNameMap = siteDiscountTemplateService.getTemplateNameMap(
                ids
        );
        return R.ok(templateNameMap);
    }

    @GetMapping(value = "", name = "查询站点折扣模板")
    public R<SiteDiscountTemplate> getTemplate(
        @RequestParam Long id
    ) {
        SiteDiscountTemplate template = siteDiscountTemplateService.getTemplate(
                id
        );
        return R.ok(template);
    }

    @PutMapping(value = "", name = "编辑站点折扣模板")
    public R<?> editTemplate(
            @RequestBody SiteDiscountTemplate siteDiscountTemplate
    ) {
        siteDiscountTemplateService.editTemplate(
                siteDiscountTemplate
        );
        return R.ok();
    }

    @DeleteMapping(value = "", name = "删除站点折扣模板")
    public R<?> deleteTemplate(
            @RequestBody SiteDiscountTemplate siteDiscountTemplate
    ) {
        siteDiscountTemplateService.deleteTemplate(
                siteDiscountTemplate.getId()
        );
        return R.ok();
    }

    @GetMapping(value = "page", name = "站点折扣模板分页列表")
    public R<PageInfo<SiteDiscountTemplateVo>> selectPage(
            SiteDiscountTemplatePageDTO pageDTO
    ) {
        PageInfo<SiteDiscountTemplateVo> page = siteDiscountTemplateService.selectPage(
                pageDTO
        );
        return R.ok(page);
    }

    @GetMapping(value = "list", name = "站点折扣模板下拉列表")
    public R<List<SiteDiscountTemplateVo>> selectTemplates(
            SiteDiscountTemplatePageDTO pageDTO
    ) {
        List<SiteDiscountTemplateVo> templates = siteDiscountTemplateService.selectTemplates(
                pageDTO
        );
        return R.ok(templates);
    }

}
