package com.things.cgomp.pay.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.pay.api.dto.RuleApiDTO;
import com.things.cgomp.device.api.dto.RuleDTO;
import com.things.cgomp.pay.api.dto.RuleSimpleDTO;
import com.things.cgomp.pay.domain.Rule;
import com.things.cgomp.pay.dto.rule.CopyRuleDTO;
import com.things.cgomp.pay.dto.rule.RulePageDTO;
import com.things.cgomp.pay.dto.rule.RuleQueryDTO;
import com.things.cgomp.pay.service.IRuleService;
import com.things.cgomp.pay.vo.RuleVo;
import com.things.cgomp.pay.vo.rule.SimpleRuleVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 收费规则表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2025-03-03
 */
@RestController
@RequestMapping("/rule")
public class RuleController {

    @Resource
    private IRuleService ruleService;

    @RequiresPermissions("pay:rule:add")
    @PostMapping(value = "", name = "新增规则")
    public R<Long> saveRule(
            @RequestBody RuleDTO ruleDTO
    ) {
        Long ruleId = ruleService.saveRule(ruleDTO);
        return R.ok(ruleId);
    }

    @PostMapping(value = "api", name = "新增规则")
    public R<Long> saveRule(
            @RequestBody RuleApiDTO apiDTO
    ) {
        Long ruleId = ruleService.saveRule(
                apiDTO
        );
        return R.ok(ruleId);
    }

    @DeleteMapping(value = "api", name = "删除规则")
    public R<?> deleteRuleB(
            @RequestBody RuleApiDTO apiDTO
    ) {
        if (apiDTO.getOperatorId() == null) {
            return R.fail("运营商ID不能为空");
        }
        ruleService.deleteRuleByOperatorId(apiDTO.getOperatorId());
        return R.ok();
    }

    @RequiresPermissions("pay:rule:copy")
    @PostMapping(value = "copy", name = "复制规则")
    public R<Long> copyRule(
            @RequestBody CopyRuleDTO ruleDTO
    ) {
        Long ruleId = ruleService.copyRule(ruleDTO);
        return R.ok(ruleId);
    }

    @RequiresPermissions("pay:rule:query")
    @GetMapping(value = "", name = "查询规则")
    public R<RuleDTO> selectRule(
          @RequestParam Long id
    ) {
        RuleDTO ruleDTO = ruleService.selectRule(id);
        return R.ok(ruleDTO);
    }

    @GetMapping(value = "model", name = "查询规则")
    public R<RuleDTO> selectRule(
            Long id,
            Integer modelId
    ) {
        RuleDTO ruleDTO = ruleService.selectRule(
                id,
                modelId
        );
        return R.ok(ruleDTO);
    }

    @GetMapping(value = "operatorDefaultRule", name = "查询运营商默认规则")
    public R<RuleDTO> selectDefaultRule(
            @RequestParam Long operatorId
    ) {
        RuleDTO ruleDTO = ruleService.selectDefaultRule(operatorId);
        return R.ok(ruleDTO);
    }

    @RequiresPermissions("pay:rule:edit")
    @PutMapping(value = "", name = "编辑规则")
    public R<Long> editRule(
            @RequestBody RuleDTO ruleDTO
    ) {
        Long ruleId = ruleService.editRule(ruleDTO);
        return R.ok(ruleId);
    }

    @RequiresPermissions("pay:rule:remove")
    @DeleteMapping(value = "", name = "删除规则")
    public R<?> deleteRule(
            @RequestBody RuleDTO ruleDTO
    ) {
        ruleService.deleteRule(ruleDTO.getId());
        return R.ok();
    }

    @GetMapping(value = "list", name = "规则列表")
    public R<List<SimpleRuleVo>> selectRules(
            RuleQueryDTO queryDTO
    ) {
        List<SimpleRuleVo> rules = ruleService.selectRules(queryDTO);
        return R.ok(rules);
    }

    @RequiresPermissions("pay:rule:list")
    @GetMapping(value = "page", name = "规则分页列表")
    public R<PageInfo<RuleVo>> selectPage(
            RulePageDTO pageDTO
    ) {
        PageInfo<RuleVo> page = ruleService.selectPage(pageDTO);
        return R.ok(page);
    }
    @GetMapping(value = "listByRules", name = "查询多个规则")
    public R<List<RuleDTO>> selectRules(
            @RequestParam List<Long> ids
    ) {
        List<RuleDTO> rules = ruleService.selectRules(ids);
        return R.ok(rules);
    }

    @GetMapping(value = "getSimpleRule", name = "查询单个规则详情")
    public R<RuleSimpleDTO> getSimpleRule(
            @RequestParam Long id
    ) {
        Rule rule = ruleService.getById(id);

        RuleSimpleDTO ruleSimpleDTO = new RuleSimpleDTO().setId(rule.getId())
                .setName(rule.getName())
                .setUpdateTime(rule.getUpdateTime());
        return R.ok(ruleSimpleDTO);
    }

}
