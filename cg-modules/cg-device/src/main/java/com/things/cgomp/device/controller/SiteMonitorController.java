package com.things.cgomp.device.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.device.dto.monitor.SiteMonitorDeviceReq;
import com.things.cgomp.device.dto.monitor.SiteMonitorDeviceResp;
import com.things.cgomp.device.service.ISiteMonitorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author things
 */
@Log(title = "站点监控")
@RestController
@RequestMapping("/siteMonitor")
public class SiteMonitorController {

    @Resource
    private ISiteMonitorService siteMonitorService;

    @RequiresPermissions("siteMonitor:device:list")
    @GetMapping(value = "/devicePage", name = "设备监测列表")
    public R<PageInfo<SiteMonitorDeviceResp>> devicePage(SiteMonitorDeviceReq req){
        return R.ok(siteMonitorService.selectDevicePage(req));
    }
}
