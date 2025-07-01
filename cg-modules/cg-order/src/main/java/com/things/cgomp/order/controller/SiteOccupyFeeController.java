package com.things.cgomp.order.controller;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.order.dto.SiteOccupyFeeDTO;
import com.things.cgomp.order.service.ISiteOccupyFeeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 站点占位费表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2025-03-02
 */
@RestController
@RequestMapping("/siteOccupyFee")
public class SiteOccupyFeeController {
    @Resource
    private ISiteOccupyFeeService siteOccupyFeeService;

    @RequiresPermissions("device:site:config:occupy")
    @PostMapping(value = "", name = "占位费设置")
    public R<Long> saveOccupyFee(
            @RequestBody SiteOccupyFeeDTO siteOccupyFee
    ) {
        Long siteId = siteOccupyFeeService.saveOccupyFee(siteOccupyFee);
        return R.ok(siteId);
    }

    @RequiresPermissions("device:site:config:occupy")
    @GetMapping(value = "", name = "查询占位费")
    public R<SiteOccupyFeeDTO> selectOccupyFee(
          @RequestParam("siteId") Long siteId
    ) {
        SiteOccupyFeeDTO siteOccupyFee = siteOccupyFeeService.selectOccupyFee(siteId);
        return R.ok(siteOccupyFee);
    }

}
