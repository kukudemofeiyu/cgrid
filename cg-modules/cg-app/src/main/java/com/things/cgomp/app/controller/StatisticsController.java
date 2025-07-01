package com.things.cgomp.app.controller;

import com.things.cgomp.app.api.domain.AppRechargeTrendData;
import com.things.cgomp.app.service.RechargeService;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.security.annotation.InnerAuth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * APP统计
 *
 * @author things
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Resource
    private RechargeService rechargeService;

    @InnerAuth
    @GetMapping("/getRechargeTrend")
    public R<List<AppRechargeTrendData>> getRechargeTrend(@RequestParam(value = "beginDate", required = false) String beginDate,
                                                          @RequestParam(value = "endDate", required = false) String endDate) {
        return R.ok(rechargeService.selectRechargeTrendData(beginDate ,endDate));
    }
}
