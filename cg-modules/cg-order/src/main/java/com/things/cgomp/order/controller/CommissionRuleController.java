package com.things.cgomp.order.controller;


import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.log.enums.BusinessType;
import com.things.cgomp.common.security.annotation.InnerAuth;
import com.things.cgomp.order.api.dto.CommissionRuleQueryDTO;
import com.things.cgomp.order.api.dto.CommissionRuleUpdateDTO;
import com.things.cgomp.order.api.domain.OrderCommissionRule;
import com.things.cgomp.order.convert.CommissionRuleConvert;
import com.things.cgomp.order.enums.ErrorCodeConstants;
import com.things.cgomp.order.service.IOrderCommissionRuleService;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.things.cgomp.common.core.utils.PageUtils.startPage;

/**
 * 分成规则管理
 *
 * @author things
 */
@Log(title = "分成规则管理")
@RestController
@RequestMapping("/commissionRule")
public class CommissionRuleController {

    @Resource
    private IOrderCommissionRuleService commissionRuleService;

    @InnerAuth
    @GetMapping("/page")
    public R<PageInfo<OrderCommissionRule>> selectPage(@SpringQueryMap CommissionRuleQueryDTO query) {
        OrderCommissionRule rule = CommissionRuleConvert.INSTANCE.convert(query);
        startPage();
        List<OrderCommissionRule> rules = commissionRuleService.selectRuleList(rule);
        return R.ok(new PageInfo<>(rules));
    }

    @InnerAuth
    @GetMapping("/list")
    public R<List<OrderCommissionRule>> selectList(OrderCommissionRule rule) {
        return R.ok(commissionRuleService.selectRuleList(rule));
    }

    @InnerAuth
    @GetMapping("/getOne")
    public R<OrderCommissionRule> selectOne(@SpringQueryMap CommissionRuleQueryDTO query) {
        OrderCommissionRule rule = CommissionRuleConvert.INSTANCE.convert(query);
        List<OrderCommissionRule> rules = commissionRuleService.selectRuleList(rule);
        if (CollectionUtils.isEmpty(rules)) {
            return R.ok();
        }
        return R.ok(rules.get(0));
    }

    @InnerAuth
    @GetMapping("/{ruleId}")
    public R<OrderCommissionRule> getInfo(@PathVariable("ruleId") Long ruleId) {
        return R.ok(commissionRuleService.selectRule(ruleId));
    }

    /**
     * 设置分成规则
     */
    @InnerAuth
    @Log(method = "设置分成规则", businessType = BusinessType.UPDATE)
    @PutMapping("")
    public R<Integer> updateRule(@RequestBody CommissionRuleUpdateDTO ruleUpdate) {
        OrderCommissionRule rule = CommissionRuleConvert.INSTANCE.convert(ruleUpdate);
        return R.ok(commissionRuleService.updateCommissionRule(rule));
    }

    /**
     * 修改分成规则状态
     */
    @InnerAuth
    @Log(method = "修改分成规则状态", businessType = BusinessType.UPDATE)
    @PutMapping("/switch")
    public R<Integer> switchStatus(@RequestBody CommissionRuleUpdateDTO ruleUpdate) {
        if (ruleUpdate.getId() == null) {
            throw exception(ErrorCodeConstants.COMMISSION_RULE_ID_NOT_NULL);
        }
        OrderCommissionRule rule = CommissionRuleConvert.INSTANCE.convert(ruleUpdate);
        return R.ok(commissionRuleService.updateStatus(rule));
    }

    /**
     * 修改分成规则状态
     */
    @InnerAuth
    @Log(method = "删除分成规则状态", businessType = BusinessType.DELETE)
    @DeleteMapping("")
    public R<Integer> deleterRule(@RequestBody CommissionRuleUpdateDTO ruleUpdate) {
        if (ruleUpdate.getId() == null) {
            throw exception(ErrorCodeConstants.COMMISSION_RULE_ID_NOT_NULL);
        }
        return R.ok(commissionRuleService.deleteRule(ruleUpdate.getId()));
    }
}
