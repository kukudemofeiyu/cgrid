package com.things.cgomp.system.api;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.system.api.domain.SysAmountRecord;
import com.things.cgomp.system.api.domain.SysWithdrawRecord;
import com.things.cgomp.system.api.factory.RemoteRecordFallbackFactory;
import com.things.cgomp.system.api.vo.AppRechargeRecordVO;
import com.things.cgomp.system.api.vo.AppRefundRecordVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 明细记录服务
 * 
 * @author things
 */
@FeignClient(contextId = "remoteRecordService",
        url = "http://localhost:9012",
        value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteRecordFallbackFactory.class)
public interface RemoteRecordService
{
    /**
     * 保存金额记录
     *
     * @param amountRecord 金额记录对象
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("/record/amount")
    R<Boolean> saveAmountRecord(@RequestBody SysAmountRecord amountRecord, @RequestHeader(SecurityConstants.FROM_SOURCE) String source) throws Exception;

    /**
     * 保存提现记录
     *
     * @param withdrawRecord 提现记录对象
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("/record/withdraw")
    R<Boolean> saveWithdrawRecord(@RequestBody SysWithdrawRecord withdrawRecord, @RequestHeader(SecurityConstants.FROM_SOURCE) String source) throws Exception;
    /**
     * app账单列表分页
     */
    @GetMapping("/record/appRechargeRecord/page")
    R<PageInfo<AppRechargeRecordVO>> appRechargeRecordPage(
            @RequestParam("userId") Long userId,
            @RequestParam("beginTime") String beginTime,
            @RequestParam("endTime") String endTime,
            @RequestParam("pageNum") Integer  pageNum,
            @RequestParam("pageSize") Integer  pageSize,
            @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
    /**
     * app提现记录分页列表
     */
    @GetMapping("/record/refundRecord/page")
     R<PageInfo<AppRefundRecordVO>> refundPage(
            @RequestParam("userId") Long userId,
            @RequestParam("status") Integer status,
            @RequestParam("beginTime") String beginTime,
            @RequestParam("endTime") String endTime,
            @RequestParam("pageNum") Integer  pageNum,
            @RequestParam("pageSize") Integer  pageSize,
            @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

}
