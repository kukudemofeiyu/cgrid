package com.things.cgomp.pay.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.web.controller.BaseController;
import com.things.cgomp.pay.api.domain.CouponTemplate;
import com.things.cgomp.pay.dto.coupon.CouponTemplateListDTO;
import com.things.cgomp.pay.dto.coupon.CouponTemplatePageDTO;
import com.things.cgomp.pay.service.ICouponTemplateService;
import com.things.cgomp.pay.vo.coupon.CouponTemplateListVo;
import com.things.cgomp.pay.vo.coupon.CouponTemplateVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 优惠券模板表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2025-03-25
 */
@RestController
@RequestMapping(value = "/couponTemplate",name = "优惠券模板")
public class CouponTemplateController extends BaseController {

    @Resource
    private ICouponTemplateService couponTemplateService;

    @GetMapping(value = "nameMap", name = "查询优惠券模板名称Map")
    R<Map<Long,String>> getNameMap(
            @RequestParam("ids") List<Long> ids
    ){
        Map<Long,String> nameMap = couponTemplateService.getNameMap(
                ids
        );
        return R.ok(nameMap);
    }

    @PostMapping(value = "", name = "新增优惠券模板")
    public R<Long> saveTemplate(
            @RequestBody CouponTemplate template
    ) {
        Long templateId = couponTemplateService.saveTemplate(
                template
        );
        return R.ok(templateId);
    }

    @GetMapping(value = "", name = "查询优惠券模板")
    public R<CouponTemplate> selectTemplate(
         @RequestParam Long id
    ) {
        CouponTemplate template = couponTemplateService.selectTemplate(
                id
        );
        return R.ok(template);
    }

    @PutMapping(value = "", name = "编辑优惠券模板")
    public R<?> editTemplate(
            @RequestBody CouponTemplate template
    ) {
        couponTemplateService.editTemplate(
                template
        );
        return R.ok();
    }

    @PutMapping(value = "switch", name = "启用/禁用优惠券模板")
    public R<?> switchTemplate(
            @RequestBody CouponTemplate template
    ) {
        couponTemplateService.switchTemplate(
                template
        );
        return R.ok();
    }

    @GetMapping(value = "page", name = "优惠券模板分页列表")
    public R<PageInfo<CouponTemplateVo>> selectPage(
            CouponTemplatePageDTO pageDTO
    ) {
        PageInfo<CouponTemplateVo> page = couponTemplateService.selectPage(
                pageDTO
        );
        return R.ok(page);
    }

    @GetMapping(value = "list", name = "优惠券模板下拉列表")
    public R<List<CouponTemplateListVo>> selectTemplates(
            CouponTemplateListDTO templateListDTO
    ) {
        List<CouponTemplateListVo> templates = couponTemplateService.selectTemplates(
                templateListDTO
        );
        return R.ok(templates);
    }

}
