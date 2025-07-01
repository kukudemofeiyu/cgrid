package com.things.cgomp.order.mapper;

import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import com.things.cgomp.order.api.domain.OrderCommissionRule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author things
 * @date 2025/3/3
 */
@Mapper
public interface OrderCommissionRuleMapper extends BaseMapperX<OrderCommissionRule> {

    int saveOrUpdate(OrderCommissionRule rule);

    OrderCommissionRule selectRuleById(Long ruleId);

    List<OrderCommissionRule> selectRuleList(OrderCommissionRule rule);

    default int updateRule(OrderCommissionRule rule){
        OrderCommissionRule updateRule = new OrderCommissionRule();
        updateRule.setId(rule.getId());
        updateRule.setRatio(rule.getRatio());
        updateRule.setType(rule.getType());
        return updateById(updateRule);
    }
}