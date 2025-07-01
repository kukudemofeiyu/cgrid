package com.things.cgomp.order.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.order.domain.ManageDeviceSiteData;
import com.things.cgomp.order.domain.ManageTotalData;
import com.things.cgomp.order.dto.ManageDataReqDTO;
import com.things.cgomp.order.service.IOrderStatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 经营管理
 *
 * @author things
 */
@Log(title = "经营管理")
@RestController
@RequestMapping("/manage")
public class ManageDataController {

    @Resource
    private IOrderStatisticsService orderStatisticsService;

    @RequiresPermissions("order:operation:total")
    @RequestMapping(value = "/getTotalData", name = "获取总数据")
    private R<ManageTotalData> getTotalData(ManageDataReqDTO reqDTO){
        return R.ok(orderStatisticsService.selectManageTotalData(reqDTO));
    }

    @RequiresPermissions("order:operation:device")
    @GetMapping(value = "/getChargeTotalData", name = "获取设备站点充电数据")
    private R<PageInfo<ManageDeviceSiteData>> getChargeTotalData(ManageDataReqDTO reqDTO){
        return R.ok(orderStatisticsService.selectManageDeviceSiteData(reqDTO));
    }
}
