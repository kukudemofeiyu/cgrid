package com.things.cgomp.system.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.log.enums.BusinessType;
import com.things.cgomp.common.security.annotation.InnerAuth;
import com.things.cgomp.system.api.domain.SysAmountRecord;
import com.things.cgomp.system.api.domain.SysWithdrawRecord;
import com.things.cgomp.system.api.dto.AppRechargeRecordDTO;
import com.things.cgomp.system.api.dto.AppRefundRecordDTO;
import com.things.cgomp.system.api.vo.AppRechargeRecordVO;
import com.things.cgomp.system.api.vo.AppRefundRecordVO;
import com.things.cgomp.system.enums.ErrorCodeConstants;
import com.things.cgomp.system.service.ISysAmountRecordService;
import com.things.cgomp.system.service.ISysWithdrawRecordService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exceptionE;
import static com.things.cgomp.common.core.utils.PageUtils.startPage;

/**
 * 明细记录管理
 *
 * @author things
 */
@Log(title = "明细记录管理")
@RestController
@RequestMapping("/record")
public class SysRecordController {

    @Resource
    private ISysAmountRecordService amountRecordService;
    @Resource
    private ISysWithdrawRecordService withdrawRecordService;

    /**
     * 内部接口
     * 保存金额记录
     */
    @InnerAuth
    @Log(method = "新增金额记录", businessType = BusinessType.INSERT)
    @PostMapping("/amount")
    public R<Integer> addAmountRecord(@Validated @RequestBody SysAmountRecord record) {
        try {
            return R.ok(amountRecordService.insertAmountRecord(record));
        } catch (Exception e) {
            throw exceptionE(ErrorCodeConstants.RECORD_AMOUNT_INSERT_ERROR, e);
        }
    }

    /**
     * 内部接口
     * 保存提现记录
     */
    @InnerAuth
    @Log(method = "新增提现记录", businessType = BusinessType.INSERT)
    @PostMapping("/withdraw")
    public R<Integer> addWithdrawRecord(@Validated @RequestBody SysWithdrawRecord record) {
        try {
            return R.ok(withdrawRecordService.insertWithdrawRecord(record));
        } catch (Exception e) {
            throw exceptionE(ErrorCodeConstants.RECORD_WITHDRAW_INSERT_ERROR, e);
        }
    }
    /**
     * app提现记录分页列表
     */
    @GetMapping("/refundRecord/page")
    @InnerAuth
    public R<PageInfo<AppRefundRecordVO>> refundPage(AppRefundRecordDTO req) {
        startPage();
        List<AppRefundRecordVO> recordList = withdrawRecordService.selectRefundRecordList(req);
        return R.ok(new PageInfo<>(recordList));
    }
    /**
     * app账单列表分页
     */
    @GetMapping("/appRechargeRecord/page")
    @InnerAuth
    public R<PageInfo<AppRechargeRecordVO>> appRechargeRecordPage(AppRechargeRecordDTO req) {
        startPage();
        List<AppRechargeRecordVO> recordList = amountRecordService.appRechargeRecordPage(req);
        return R.ok(new PageInfo<>(recordList));
    }
}
