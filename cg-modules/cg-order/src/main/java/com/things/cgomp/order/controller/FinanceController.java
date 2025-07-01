package com.things.cgomp.order.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.order.api.domain.CommissionRecordStatisticsData;
import com.things.cgomp.order.convert.OrderFinanceConvert;
import com.things.cgomp.order.api.domain.OrderFinanceData;
import com.things.cgomp.order.api.domain.OrderFinanceTotalData;
import com.things.cgomp.order.dto.CommissionRecordStatisticsQueryDTO;
import com.things.cgomp.order.dto.OrderFinanceQueryDTO;
import com.things.cgomp.order.service.IOrderCommissionRecordService;
import com.things.cgomp.order.service.IOrderFinanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 财务管理
 *
 * @author things
 */
@Log(title = "财务管理")
@RestController
@RequestMapping("/finance")
public class FinanceController {

    @Resource
    private IOrderFinanceService orderFinanceService;
    @Resource
    private IOrderCommissionRecordService commissionRecordService;

    @RequiresPermissions("order:finance:total")
    @GetMapping(value = "/getTotal", name = "获取累计销售额")
    public R<OrderFinanceTotalData> getTotal(OrderFinanceQueryDTO queryDTO) {
        OrderFinanceData data = orderFinanceService.selectTotalData(queryDTO);
        return R.ok(OrderFinanceConvert.INSTANCE.convertTotal(data));
    }

    @RequiresPermissions("order:finance:list")
    @GetMapping(value = "/page", name = "分页查询统计销售额")
    public R<PageInfo<OrderFinanceData>> page(OrderFinanceQueryDTO queryDTO){
        return R.ok(orderFinanceService.selectPage(queryDTO));
    }

    @RequiresPermissions("order:finance:commission:operator")
    @GetMapping(value = "/commission/operator", name = "分页获取运营商分账汇总数据")
    public R<PageInfo<CommissionRecordStatisticsData>> commissionRecordOperatorPage(CommissionRecordStatisticsQueryDTO queryDTO){
        queryDTO.setType(1);
        return R.ok(commissionRecordService.selectRecordStatisticsPage(queryDTO));
    }

    @RequiresPermissions("order:finance:commission:site")
    @GetMapping(value = "/commission/site", name = "分页获取站点分账汇总数据")
    public R<PageInfo<CommissionRecordStatisticsData>> commissionRecordSitePage(CommissionRecordStatisticsQueryDTO queryDTO){
        queryDTO.setType(2);
        return R.ok(commissionRecordService.selectRecordStatisticsPage(queryDTO));
    }
}
