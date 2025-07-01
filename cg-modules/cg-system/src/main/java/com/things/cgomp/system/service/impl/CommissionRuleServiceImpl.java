package com.things.cgomp.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.common.core.enums.CommissionLevel;
import com.things.cgomp.common.mybatisplus.query.LambdaQueryWrapperX;
import com.things.cgomp.order.api.domain.OrderCommissionRule;
import com.things.cgomp.system.mapper.CommissionRuleMapper;
import com.things.cgomp.system.service.ICommissionRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author things
 * @date 2025/3/3
 */
@Slf4j
@Service
public class CommissionRuleServiceImpl extends ServiceImpl<CommissionRuleMapper, OrderCommissionRule> implements ICommissionRuleService {


    @Override
    public BigDecimal selectSurplusRatio(CommissionLevel commissionLevel) {
        BigDecimal allPercent = new BigDecimal(100);
        BigDecimal sumRatio = baseMapper.selectSumByLevel(commissionLevel.getLevel());
        if (sumRatio == null) {
            // 无子分成者信息或未分配分成
            return allPercent;
        }
        BigDecimal surplusRatio = allPercent.subtract(sumRatio);
        if (surplusRatio.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        return surplusRatio.setScale(2, RoundingMode.DOWN);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCommissionRule(OrderCommissionRule rule) {
        CommissionLevel commissionLevel = CommissionLevel.getByLevel(rule.getLevel());
        if (commissionLevel == null) {
            log.error("updateCommissionRule error, 规则等级不匹配");
            return 0;
        }
        return baseMapper.saveOrUpdate(rule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByUserId(Long userId) {
        LambdaQueryWrapperX<OrderCommissionRule> wrapperX = new LambdaQueryWrapperX<OrderCommissionRule>()
                .eq(OrderCommissionRule::getUserId, userId);
        return baseMapper.delete(wrapperX);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByOperatorId(Long operatorId) {
        LambdaQueryWrapperX<OrderCommissionRule> wrapperX = new LambdaQueryWrapperX<OrderCommissionRule>()
                .eq(OrderCommissionRule::getOperatorId, operatorId);
        return baseMapper.delete(wrapperX);
    }
}
