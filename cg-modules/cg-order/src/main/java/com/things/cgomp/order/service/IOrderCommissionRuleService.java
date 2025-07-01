package com.things.cgomp.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.things.cgomp.order.api.domain.OrderCommissionRule;

import java.util.List;

/**
 * @author things
 * @date 2025/3/3
 */
public interface IOrderCommissionRuleService extends IService<OrderCommissionRule> {

    /**
     * 根据ID查询规则
     * @param ruleId 规则ID
     * @return
     */
    OrderCommissionRule selectRule(Long ruleId);

    /**
     * 获取分成规则列表
     * @param rule 请求参数
     * @return
     */
    List<OrderCommissionRule> selectRuleList(OrderCommissionRule rule);

    /**
     * 获取运营商分成规则
     * @param operatorId 运营商ID
     * @return List<OrderCommissionRule>
     */
    List<OrderCommissionRule> selectOperatorRuleList(Long operatorId);

    /**
     * 设置分成规则
     * @param rule 规则对象
     * @return
     */
    int updateCommissionRule(OrderCommissionRule rule);

    /**
     * 更新规则状态
     * @param rule 规则对象
     * @return
     */
    int updateStatus(OrderCommissionRule rule);

    /**
     * 删除规则
     * @param ruleId 规则ID
     * @return int
     */
    int deleteRule(Long ruleId);
}
