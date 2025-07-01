package com.things.cgomp.system.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.log.enums.BusinessType;
import com.things.cgomp.common.security.annotation.Idempotent;
import com.things.cgomp.common.security.annotation.InnerAuth;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.common.security.idempotent.impl.TokenIdempotentKeyResolver;
import com.things.cgomp.system.api.domain.SysAmountRecord;
import com.things.cgomp.system.api.dto.AppRechargeDTO;
import com.things.cgomp.system.convert.AmountRecordConvert;
import com.things.cgomp.system.domain.dto.AppRechargeOrderDTO;
import com.things.cgomp.system.domain.dto.AppUserRechargeReq;
import com.things.cgomp.system.domain.dto.AppUserRefundReq;
import com.things.cgomp.system.domain.dto.SysAmountRecordReq;
import com.things.cgomp.system.domain.vo.AppRechargeOrderVO;
import com.things.cgomp.system.domain.vo.AppUserRechargeRecordVO;
import com.things.cgomp.system.service.IAppRechargeOrderService;
import com.things.cgomp.system.service.IAppUserService;
import com.things.cgomp.system.service.ISysAmountRecordService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.things.cgomp.common.core.utils.PageUtils.startPage;

/**
 * 注册用户充值管理
 *
 * @author things
 */
@Log(title = "注册用户充值管理")
@RestController
@RequestMapping("/register")
public class AppUserRechargeController {

    @Resource
    private IAppUserService appUserService;
    @Resource
    private ISysAmountRecordService amountRecordService;
    @Resource
    private IAppRechargeOrderService appRechargeOrderService;

    /**
     * 系统充值
     */
    @Idempotent(timeout = 5, keyResolver = TokenIdempotentKeyResolver.class)
    @Log(method = "系统充值", businessType = BusinessType.RECHARGE)
    @RequiresPermissions("app:register:recharge")
    @PostMapping("/recharge")
    public R<Integer> recharge(@Validated @RequestBody AppUserRechargeReq req) {
        return R.ok(appUserService.systemRecharge(req));
    }
    /**
     *APP充值
     */
     @Log(method = "APP充值", businessType = BusinessType.RECHARGE)
     @PostMapping("/appRecharge")
     @InnerAuth
     public R<Integer> appRecharge( @RequestBody AppRechargeDTO req) {
         return R.ok(appUserService.appRecharge(req));
     }
    /**
     * 充值记录分页列表
     */
    @RequiresPermissions("app:register:record:list")
    @GetMapping("/rechargeRecord/page")
    public R<PageInfo<AppUserRechargeRecordVO>> page(SysAmountRecordReq req) {
        startPage();
        List<SysAmountRecord> recordList = amountRecordService.selectRecordList(req);
        List<AppUserRechargeRecordVO> respList = AmountRecordConvert.INSTANCE.convertList(recordList);
        return R.ok(new PageInfo<>(respList));
    }
    /**
     * 查询单个充值记录
     */
    @RequiresPermissions("app:register:record:query")
    @GetMapping("/rechargeRecord")
    public R<AppUserRechargeRecordVO> getInfo(@RequestParam("id") Long id) {
        SysAmountRecord record = amountRecordService.selectRecordById(id);
        return R.ok(AmountRecordConvert.INSTANCE.convert(record));
    }
    /**
     * 充值订单分页查询
     */
    @RequiresPermissions("app:register:order:list")
    @GetMapping("/order/page")
    public R<PageInfo<AppRechargeOrderVO>> orderPage(AppRechargeOrderDTO req) {
        startPage();
        List<AppRechargeOrderVO> orderList = appRechargeOrderService.selectOrderList(req);
        return R.ok(new PageInfo<>(orderList));
    }

    /**
     * 操作退款
     */
    @Log(method = "退款", businessType = BusinessType.RECHARGE)
    @RequiresPermissions("app:register:record:refund")
    @PostMapping("/refund")
    public R<Integer> refund(@Validated @RequestBody AppUserRefundReq req) {
        return R.ok(appUserService.refund(req));
    }
}