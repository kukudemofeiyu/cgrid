package com.things.cgomp.pay.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.pay.api.domain.SiteDiscountActivity;
import com.things.cgomp.pay.dto.sitediscount.SiteDiscountActivityPageDTO;
import com.things.cgomp.pay.service.ISiteDiscountActivityService;
import com.things.cgomp.pay.vo.sitediscount.SiteDiscountActivityVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 站点折扣活动表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
@RestController
@RequestMapping(value = "/siteDiscountActivity",name = "站点折扣活动")
public class SiteDiscountActivityController {

    @Resource
    private ISiteDiscountActivityService siteDiscountActivityService;

    @PostMapping(value = "", name = "新增站点折扣活动")
    public R<Long> saveActivity(
           @Validated @RequestBody SiteDiscountActivity activity
    ) {
        Long templateId = siteDiscountActivityService.saveActivity(
                activity
        );
        return R.ok(templateId);
    }

    @GetMapping(value = "activityNameMap", name = "查询站点活动名称Map")
    R<Map<Long, String>> getActivityNameMap(
            @RequestParam("ids") List<Long> ids
    ) {
        Map<Long, String> activityNameMap = siteDiscountActivityService.getActivityNameMap(
                ids
        );
        return R.ok(activityNameMap);
    }

    @GetMapping(value = "", name = "查询站点折扣活动")
    public R<SiteDiscountActivity> getActivity(
         @RequestParam Long id
    ) {
        SiteDiscountActivity activity = siteDiscountActivityService.getActivity(
                id
        );
        return R.ok(activity);
    }

    @PutMapping(value = "", name = "编辑站点折扣活动")
    public R<?> editActivity(
           @Validated @RequestBody SiteDiscountActivity activity
    ) {
        siteDiscountActivityService.editActivity(
                activity
        );
        return R.ok();
    }

    @DeleteMapping(value = "", name = "删除站点折扣活动")
    public R<?> deleteActivity(
            @RequestBody SiteDiscountActivity activity
    ) {
        siteDiscountActivityService.deleteActivity(
                activity.getId()
        );
        return R.ok();
    }

    @PutMapping(value = "switch", name = "禁用/启用")
    public R<?> switchActivity(
            @RequestBody SiteDiscountActivity activity
    ) {
        siteDiscountActivityService.switchActivity(
                activity.getId(),
                activity.getStatus()
        );
        return R.ok();
    }

    @GetMapping(value = "page", name = "站点活动分页列表")
    public R<PageInfo<SiteDiscountActivityVo>> selectPage(
            SiteDiscountActivityPageDTO pageDTO
    ) {
        PageInfo<SiteDiscountActivityVo> page = siteDiscountActivityService.selectPage(
                pageDTO
        );
        return R.ok(page);
    }

}
