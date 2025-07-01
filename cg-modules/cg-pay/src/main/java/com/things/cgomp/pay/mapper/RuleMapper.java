package com.things.cgomp.pay.mapper;

import com.things.cgomp.common.core.web.domain.BaseEntity;
import com.things.cgomp.pay.domain.Rule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.pay.dto.rule.RulePageDTO;
import com.things.cgomp.pay.dto.rule.RuleQueryDTO;
import com.things.cgomp.pay.vo.RuleVo;
import com.things.cgomp.pay.vo.rule.SimpleRuleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 收费规则表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-03-03
 */
public interface RuleMapper extends BaseMapper<Rule> {

    List<RuleVo> selectRules(
            RulePageDTO pageDTO
    );

    Rule selectRule(
            @Param("id") Long id
    );

    Rule selectModelRule(
            @Param("id") Long id,
            @Param("modelId") Integer modelId
    );

    Integer updateRule(
            Rule rule
    );

    Integer deleteRule(
            @Param("id") Long id
    );

    List<SimpleRuleVo> selectSimpleRules(BaseEntity baseEntity);

    List<SimpleRuleVo> selectSimpleRulesByPile(RuleQueryDTO queryDTO);

    Set<Integer> selectNormalModelIds(
            @Param("operatorId") Long operatorId
    );

    Integer selectDeletedMaxModelId(
            @Param("operatorId") Long operatorId
    );

    Rule selectOperatorDefaultRule(
            @Param("operatorId") Long operatorId
    );

    Rule selectSysDefaultRule();
}
