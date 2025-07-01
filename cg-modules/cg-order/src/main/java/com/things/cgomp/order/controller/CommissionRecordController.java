package com.things.cgomp.order.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.order.api.domain.CommissionRecordData;
import com.things.cgomp.order.convert.CommissionRecordConvert;
import com.things.cgomp.order.dto.CommissionRecordQueryDTO;
import com.things.cgomp.order.service.IOrderCommissionRecordService;
import com.things.cgomp.order.vo.CommissionRecordTotalVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 分成规则管理
 *
 * @author things
 */
@Log(title = "分成记录管理")
@RestController
@RequestMapping("/commissionRecord")
public class CommissionRecordController {

    @Resource
    private IOrderCommissionRecordService commissionRecordService;

    @RequiresPermissions("system:operator:commission:total")
    @GetMapping(value = "/getTotal", name = "获取分成记录累计数据")
    public R<CommissionRecordTotalVO> getTotal(CommissionRecordQueryDTO queryDTO){
        CommissionRecordData totalData = commissionRecordService.getTotalData(queryDTO);
        return R.ok(CommissionRecordConvert.INSTANCE.convertTotal(totalData));
    }

    @RequiresPermissions("system:operator:commission:list")
    @GetMapping(value = "/page", name = "分页获取分成明细记录")
    public R<PageInfo<CommissionRecordData>> getPage(CommissionRecordQueryDTO queryDTO){
        return R.ok(commissionRecordService.selectRecordPage(queryDTO));
    }
}
