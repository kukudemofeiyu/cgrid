package com.things.cgomp.device.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.enums.CommissionLevel;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.log.enums.BusinessType;
import com.things.cgomp.common.security.annotation.InnerAuth;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.device.api.domain.Site;
import com.things.cgomp.device.api.model.vo.SiteAppInfoVO;
import com.things.cgomp.device.api.model.vo.SiteAppVO;
import com.things.cgomp.device.dto.site.SiteListDTO;
import com.things.cgomp.device.dto.site.SitePageDTO;
import com.things.cgomp.device.enums.ErrorCodeConstants;
import com.things.cgomp.device.service.ISiteService;
import com.things.cgomp.device.vo.SiteVo;
import com.things.cgomp.device.vo.site.SimpleSiteVo;
import com.things.cgomp.order.api.RemoteCommissionRuleService;
import com.things.cgomp.order.api.dto.CommissionRuleQueryDTO;
import com.things.cgomp.order.api.dto.CommissionRuleUpdateDTO;
import com.things.cgomp.order.api.domain.OrderCommissionRule;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * <p>
 * 站点表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2025-02-28
 */
@Log(title = "站点管理")
@RestController
@RequestMapping("/site")
public class SiteController {

    @Resource
    private ISiteService siteService;
    @Resource
    private RemoteCommissionRuleService remoteCommissionRuleService;

    @RequiresPermissions("device:site:add")
    @PostMapping(value = "", name = "新增站点")
    public R<Long> addSite(
            @RequestBody Site site
    ) {
        Long siteId = siteService.addSite(site);
        return R.ok(siteId);
    }

    @RequiresPermissions("device:site:edit")
    @PutMapping(value = "", name = "编辑站点")
    public R<?> editSite(
            @RequestBody Site site
    ) {
        siteService.editSite(site);
        return R.ok();
    }

    @RequiresPermissions("device:site:remove")
    @DeleteMapping(value = "", name = "删除站点")
    public R<?> deleteSite(
            @RequestBody Site site
    ) {
        siteService.deleteSite(site.getId());
        return R.ok();
    }

    @RequiresPermissions("device:site:query")
    @GetMapping(value = "", name = "查询站点信息")
    public R<Site> selectSite(
            @RequestParam Long id
    ) {
        Site site = siteService.selectSite(id);
        return R.ok(site);
    }

    @GetMapping(value = "list", name = "站点列表")
    public R<List<SimpleSiteVo>> selectSimpleSites(
            SiteListDTO siteDTO
    ) {
        List<SimpleSiteVo> sites = siteService.selectSimpleSites(siteDTO);
        return R.ok(sites);
    }

    @GetMapping(value = "siteMap", name = "查询站点Map")
    R<Map<Long,Site>> selectSiteMap(
            @RequestParam(value = "ids", required = false) List<Long> ids
    ){
        Map<Long,Site> siteMap = siteService.selectSiteMap(ids);
        return R.ok(siteMap);
    }

    @GetMapping(value = "operatorId", name = "查询运营商id")
    R<Long> getOperatorId(
            @RequestParam(value = "id") Long id
    ) {
        Long operatorId = siteService.getOperatorId(id);
        return R.ok(operatorId);
    }

    @RequiresPermissions("device:site:list")
    @GetMapping(value = "page", name = "站点分页列表")
    public R<PageInfo<SiteVo>> selectPage(
            SitePageDTO sitePageDTO
    ) {
        PageInfo<SiteVo> page = siteService.selectPage(sitePageDTO);
        return R.ok(page);
    }
    @InnerAuth
    @GetMapping(value = "appList", name = "站点信息列表")
    public R<List<SiteAppVO>> selectSiteAppList(
            SiteListDTO siteListDTO
    ) {
        List<SiteAppVO> siteList = siteService.selectSiteAppList(siteListDTO);
        return R.ok(siteList);
    }
    @InnerAuth
    @GetMapping(value = "appInfo", name = "站点信息详情")
    public R<SiteAppInfoVO> selectSiteInfo(
            @RequestParam(value = "siteId", required = false) Long siteId,
            @RequestParam(value = "lat", required = false) Float lat,
            @RequestParam(value = "lng", required = false) Float lng
    ){
        SiteAppInfoVO siteInfoVO = siteService.selectSiteAppInfo(siteId,lat,lng);
        return R.ok(siteInfoVO);
    }

    /**
     * 查看分成设置
     */
    @RequiresPermissions("device:site:config:commission")
    @GetMapping("/commission")
    public R<OrderCommissionRule> getCommission(@RequestParam("siteId") Long siteId) {
        CommissionRuleQueryDTO ruleQuery = CommissionRuleQueryDTO.builder()
                .siteId(siteId)
                .operatorId(siteService.getSiteOperatorId(siteId))
                .level(CommissionLevel.OPERATOR_SITE.getLevel())
                .build();
        R<OrderCommissionRule> ruleResp = remoteCommissionRuleService.selectOne(ruleQuery, SecurityConstants.INNER);
        if (R.SUCCESS != ruleResp.getCode()) {
            return R.fail();
        }
        return R.ok(ruleResp.getData());
    }

    /**
     * 分成设置
     */
    @RequiresPermissions("device:site:config:commission")
    @Log(method = "站点设置分成", businessType = BusinessType.UPDATE)
    @PutMapping("/commission")
    public R<Integer> editCommission(@Validated @RequestBody CommissionRuleUpdateDTO ruleUpdate) {
        ruleUpdate.setOperatorId(siteService.getSiteOperatorId(ruleUpdate.getSiteId()));
        ruleUpdate.setLevel(CommissionLevel.OPERATOR_SITE.getLevel());
        R<Integer> respR = remoteCommissionRuleService.updateRule(ruleUpdate, SecurityConstants.INNER);
        if (R.SUCCESS != respR.getCode()) {
            throw exception(ErrorCodeConstants.SITE_COMMISSION_SET_FAIL);
        }
        return R.ok(respR.getData());
    }
}
