package com.things.cgomp.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.things.cgomp.common.core.enums.CommissionLevel;
import com.things.cgomp.order.api.domain.OrderCommissionRule;

import java.math.BigDecimal;

/**
 * @author things
 * @date 2025/3/3
 */
public interface ICommissionRuleService extends IService<OrderCommissionRule> {

    /**
     * 根据运营商查询子分成者剩余比例
     * @param commissionLevel 规则级别
     * @return
     */
    BigDecimal selectSurplusRatio(CommissionLevel commissionLevel);

    /**
     * 设置分成规则
     * 目前此接口只针对分成者配置使用
     * @param rule 规则对象
     * @return
     */
    int updateCommissionRule(OrderCommissionRule rule);

    /**
     * 根据用户ID删除规则
     * @param userId 用户ID
     * @return
     */
    int deleteByUserId(Long userId);

    /**
     * 根据运营商ID删除规则
     * @param operatorId 运营商ID
     * @return
     */
    int deleteByOperatorId(Long operatorId);
}
