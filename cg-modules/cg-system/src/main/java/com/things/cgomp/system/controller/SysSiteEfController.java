package com.things.cgomp.system.controller;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.security.annotation.InnerAuth;
import com.things.cgomp.system.api.domain.SysSiteEf;
import com.things.cgomp.system.service.ISysSiteEfService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 站点关联信息
 *
 * @author things
 */
@Log(title = "站点关联管理")
@RestController
@RequestMapping("/siteEf")
public class SysSiteEfController {

    @Resource
    private ISysSiteEfService siteEfService;

    /**
     * 内部接口
     * 根据用户ID查询站点信息

     * @return 站点ID集合
     */
    @InnerAuth
    @GetMapping("/getSiteIdsByUserId")
    public R<List<Long>> getUserSiteIds(@RequestParam("userId") Long userId) {
        return R.ok(siteEfService.selectSiteIdsByUserId(userId));
    }

    /**
     * 内部接口
     * 添加站点关联，组织、用户
     * @return 站点ID集合
     */
    @InnerAuth
    @PostMapping()
    public R<Integer> addSiteEf(@RequestBody SysSiteEf sysSiteEf) {
        return R.ok(siteEfService.addSiteEf(sysSiteEf));
    }

    /**
     * 内部接口
     * 修改站点关联，组织、用户
     * @return 站点ID集合
     */
    @InnerAuth
    @PutMapping()
    public R<Integer> updateSiteEf(@RequestBody SysSiteEf sysSiteEf) {
        return R.ok(siteEfService.updateSiteEf(sysSiteEf));
    }

    /**
     * 内部接口
     * 根据站点ID删除关联
     * @param siteId 站点ID
     * @return Integer
     */
    @InnerAuth
    @DeleteMapping("/deleteBySiteId/{siteId}")
    public R<Integer> deleteSiteEfBySiteId(@PathVariable("siteId") Long siteId){
        return R.ok(siteEfService.deleteSiteEfBySite(siteId));
    }
}
