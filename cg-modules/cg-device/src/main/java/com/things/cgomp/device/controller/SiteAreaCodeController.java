package com.things.cgomp.device.controller;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.device.service.ISiteAreaCodeService;
import com.things.cgomp.device.vo.AreaTreeNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 地区编码表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2025-02-28
 */
@RestController
@RequestMapping("/siteAreaCode")
public class SiteAreaCodeController {

    @Resource
    private ISiteAreaCodeService siteAreaCodeService;

    @GetMapping(value = "/list",name = "获取地区编码")
    public R<List<AreaTreeNode>> getAreaCodes(){
        List<AreaTreeNode> areaCode = siteAreaCodeService.getAreaCodes();
        return R.ok(areaCode);
    }


}
