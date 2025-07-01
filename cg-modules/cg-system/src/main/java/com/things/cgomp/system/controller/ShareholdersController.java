package com.things.cgomp.system.controller;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.enums.CommissionLevel;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.log.enums.BusinessType;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.order.api.RemoteCommissionRuleService;
import com.things.cgomp.order.api.dto.CommissionRuleQueryDTO;
import com.things.cgomp.order.api.dto.CommissionRuleUpdateDTO;
import com.things.cgomp.order.api.domain.OrderCommissionRule;
import com.things.cgomp.order.api.domain.OrderShareholders;
import com.things.cgomp.system.convert.ShareholdersConvert;
import com.things.cgomp.system.domain.dto.CommissionRuleAdd;
import com.things.cgomp.system.domain.vo.ShareholdersSimpleVO;
import com.things.cgomp.system.enums.ErrorCodeConstants;
import com.things.cgomp.system.service.ICommissionRuleService;
import com.things.cgomp.system.service.IShareholdersService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 分成者管理
 *
 * @author things
 */
@Log(title = "分成者管理")
@RestController
@RequestMapping("/shareholders")
public class ShareholdersController {

    @Resource
    private IShareholdersService shareholdersService;
    @Resource
    private ICommissionRuleService commissionRuleService;
    @Resource
    private RemoteCommissionRuleService remoteCommissionRuleService;

    /**
     * 获取分成者分页列表
     */
    @RequiresPermissions("order:shareholders:list")
    @GetMapping("/page")
    public R<PageInfo<OrderShareholders>> list(OrderShareholders shareholders) {
        PageInfo<OrderShareholders> pageInfo = shareholdersService.selectPage(shareholders);
        return R.ok(pageInfo);
    }

    /**
     * 获取分成者简约列表
     * 用于下拉框
     */
    @RequiresPermissions("order:shareholders:list")
    @GetMapping(value = "/simple-list")
    public R<List<ShareholdersSimpleVO>> selectSimpleList() {
        List<OrderShareholders> shareholders = shareholdersService.selectList(new OrderShareholders());
        return R.ok(ShareholdersConvert.INSTANCE.convertList(shareholders));
    }

    /**
     * 新增分成者
     */
    @RequiresPermissions("order:shareholders:add")
    @Log(method = "新增分成者", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@Validated @RequestBody OrderShareholders shareholders) {
        if (ObjectUtil.isEmpty(shareholders.getUsername())) {
            throw exception(ErrorCodeConstants.USER_USERNAME_NOT_NULL);
        }
        return R.ok(shareholdersService.insertShareholders(shareholders));
    }

    /**
     * 根据分成者编号获取详细信息
     */
    @RequiresPermissions("order:shareholders:query")
    @GetMapping(value = {""})
    public R<OrderShareholders> getInfo(OrderShareholders req) {
        OrderShareholders shareholders = shareholdersService.selectShareholdersById(req.getId());
        if (shareholders == null) {
            return R.ok();
        }
        // 获取剩余分配比例
        BigDecimal surplusRadio = commissionRuleService.selectSurplusRatio(CommissionLevel.SHAREHOLDERS);
        shareholders.setSurplusRadio(surplusRadio);
        return R.ok(shareholders);
    }

    @GetMapping("/getSurplusRadio")
    public R<OrderShareholders> getSurplusRadio(){
        // 获取当前运营商剩余分配比例
        BigDecimal surplusRadio = commissionRuleService.selectSurplusRatio(CommissionLevel.SHAREHOLDERS);
        OrderShareholders resp = new OrderShareholders().setSurplusRadio(surplusRadio);
        return R.ok(resp);
    }


    /**
     * 删除分成者
     */
    @RequiresPermissions("order:shareholders:remove")
    @Log(method = "删除分成者", businessType = BusinessType.DELETE)
    @DeleteMapping("")
    public R<Integer> remove(@RequestBody OrderShareholders req) {
        if (req.getId() == null) {
            throw exception(ErrorCodeConstants.COMMISSION_ID_IS_NOT_NULL);
        }
        return R.ok(shareholdersService.deleteShareholdersById(req.getId()));
    }

    /**
     * 查看分成设置列表
     */
    @RequiresPermissions("order:shareholders:commission:list")
    @GetMapping("/commission/page")
    public R<PageInfo<OrderCommissionRule>> getCommissionPage(CommissionRuleQueryDTO ruleQuery) {
        ruleQuery.setLevel(CommissionLevel.SHAREHOLDERS_SITE.getLevel());
        R<PageInfo<OrderCommissionRule>> pageR = remoteCommissionRuleService.selectPage(ruleQuery, SecurityConstants.INNER);
        if (R.SUCCESS != pageR.getCode()) {
            return R.ok(new PageInfo<>(new ArrayList<>()));
        }
        return R.ok(pageR.getData());
    }

    /**
     * 查看分成设置
     */
    @RequiresPermissions("order:shareholders:commission:edit")
    @GetMapping("/commission")
    public R<OrderCommissionRule> getCommission(OrderCommissionRule req) {
        R<OrderCommissionRule> ruleR = remoteCommissionRuleService.selectById(req.getId(), SecurityConstants.INNER);
        return R.ok(ruleR.getData());
    }

    /**
     * 添加分成比例
     */
    @RequiresPermissions("order:shareholders:commission:add")
    @Log(method = "添加分成者-站点分成比例", businessType = BusinessType.INSERT)
    @PostMapping("/commission")
    public R<Integer> addCommission(@Validated @RequestBody CommissionRuleAdd ruleAdd) {
        CommissionRuleUpdateDTO updateRule = ShareholdersConvert.INSTANCE.convertRuleUpt(ruleAdd);
        updateRule.setLevel(CommissionLevel.SHAREHOLDERS_SITE.getLevel());
        R<Integer> respR = remoteCommissionRuleService.updateRule(updateRule, SecurityConstants.INNER);
        if (R.SUCCESS != respR.getCode()) {
            throw exception(ErrorCodeConstants.SHAREHOLDERS_COMMISSION_ADD_FAIL);
        }
        return R.ok(respR.getData());
    }

    /**
     * 设置分成比例
     */
    @RequiresPermissions("order:shareholders:commission:edit")
    @Log(method = "设置分成者-站点分成比例", businessType = BusinessType.UPDATE)
    @PutMapping("/commission")
    public R<Integer> editCommission(@RequestBody CommissionRuleUpdateDTO ruleUpdate) {
        if (ObjectUtil.isEmpty(ruleUpdate.getId())) {
            throw exception(ErrorCodeConstants.COMMISSION_RULE_ID_IS_NOT_NULL);
        }
        R<Integer> respR = remoteCommissionRuleService.updateRule(ruleUpdate, SecurityConstants.INNER);
        if (R.SUCCESS != respR.getCode()) {
            throw exception(ErrorCodeConstants.SHAREHOLDERS_COMMISSION_SET_FAIL);
        }
        return R.ok(respR.getData());
    }

    /**
     * 设置分成比例
     */
    @RequiresPermissions("order:shareholders:commission:edit")
    @Log(method = "设置分成者-站点状态", businessType = BusinessType.UPDATE)
    @PutMapping("/commission/switch")
    public R<Integer> editCommissionStatus(@RequestBody CommissionRuleUpdateDTO ruleUpdate) {
        if (ObjectUtil.isEmpty(ruleUpdate.getId())) {
            throw exception(ErrorCodeConstants.COMMISSION_RULE_ID_IS_NOT_NULL);
        }
        R<Integer> respR = remoteCommissionRuleService.switchCommissionRule(ruleUpdate, SecurityConstants.INNER);
        if (R.SUCCESS != respR.getCode()) {
            throw exception(ErrorCodeConstants.SHAREHOLDERS_COMMISSION_SET_FAIL);
        }
        return R.ok(respR.getData());
    }

    /**
     * 设置分成比例
     */
    @RequiresPermissions("order:shareholders:commission:delete")
    @Log(method = "删除分成者-站点状态", businessType = BusinessType.DELETE)
    @DeleteMapping("/commission")
    public R<Integer> deleteCommission(@RequestBody CommissionRuleUpdateDTO ruleUpdate) {
        if (ObjectUtil.isEmpty(ruleUpdate.getId())) {
            throw exception(ErrorCodeConstants.COMMISSION_RULE_ID_IS_NOT_NULL);
        }
        R<Integer> respR = remoteCommissionRuleService.deleteCommissionRule(ruleUpdate, SecurityConstants.INNER);
        if (R.SUCCESS != respR.getCode()) {
            throw exception(ErrorCodeConstants.SHAREHOLDERS_COMMISSION_SET_FAIL);
        }
        return R.ok(respR.getData());
    }

    /**
     * 修改分成者状态
     */
    @RequiresPermissions("order:shareholders:edit")
    @Log(method = "修改分成者状态", businessType = BusinessType.UPDATE)
    @PutMapping("/switch")
    public R<Integer> switchStatus(@RequestBody OrderShareholders updateReq) {
        if (updateReq.getId() == null) {
            throw exception(ErrorCodeConstants.COMMISSION_ID_IS_NOT_NULL);
        }
        return R.ok(shareholdersService.updateStatus(updateReq));
    }

    /**
     * 修改分成者信息
     */
    @RequiresPermissions("order:shareholders:edit")
    @Log(method = "修改分成者信息", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Integer> edit(@Validated @RequestBody OrderShareholders updateReq) {
        if (updateReq.getId() == null) {
            throw exception(ErrorCodeConstants.COMMISSION_ID_IS_NOT_NULL);
        }
        return R.ok(shareholdersService.updateShareholders(updateReq));
    }
}
