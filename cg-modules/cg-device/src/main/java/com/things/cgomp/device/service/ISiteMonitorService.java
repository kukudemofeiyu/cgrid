package com.things.cgomp.device.service;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.device.dto.monitor.SiteMonitorDeviceReq;
import com.things.cgomp.device.dto.monitor.SiteMonitorDeviceResp;

/**
 * @author things
 */
public interface ISiteMonitorService {

    PageInfo<SiteMonitorDeviceResp> selectDevicePage(SiteMonitorDeviceReq req);
}
