package com.things.cgomp.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.pay.api.dto.RuleApiDTO;
import com.things.cgomp.device.api.dto.RuleDTO;
import com.things.cgomp.pay.domain.Rule;
import com.things.cgomp.pay.dto.rule.CopyRuleDTO;
import com.things.cgomp.pay.dto.rule.RulePageDTO;
import com.things.cgomp.pay.dto.rule.RuleQueryDTO;
import com.things.cgomp.pay.vo.RuleVo;
import com.things.cgomp.pay.vo.rule.SimpleRuleVo;

import java.util.List;

/**
 * <p>
 * 收费规则表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-03
 */
public interface IRuleService extends IService<Rule> {

    Long copyRule(
            CopyRuleDTO ruleDTO
    );

    Long saveRule(
            RuleDTO ruleDTO
    );

    Long saveRule(
            RuleApiDTO apiDTO
    );

    RuleDTO selectRule(
            Long id
    );

    RuleDTO selectRule(
            Long id,
            Integer modelId
    );

    RuleDTO selectDefaultRule(
            Long operatorId
    );

    Long editRule(
            RuleDTO ruleDTO
    );

    void deleteRule(
          Long ruleId
    );

    void deleteRuleByOperatorId(
            Long operatorId
    );

    PageInfo<RuleVo> selectPage(
            RulePageDTO pageDTO
    );

    List<SimpleRuleVo> selectRules(
            RuleQueryDTO queryDTO
    );

    List<RuleDTO> selectRules(List<Long> ids);
}
