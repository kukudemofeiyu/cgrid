package com.things.cgomp.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.enums.CommissionLevel;
import com.things.cgomp.common.core.enums.CommonStatus;
import com.things.cgomp.common.core.exception.enums.GlobalErrorCodeConstants;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.mybatisplus.query.LambdaQueryWrapperX;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.order.api.domain.OrderCommissionRule;
import com.things.cgomp.order.api.domain.OrderShareholders;
import com.things.cgomp.order.enums.ErrorCodeConstants;
import com.things.cgomp.order.mapper.OrderCommissionRuleMapper;
import com.things.cgomp.order.service.IOrderCommissionRuleService;
import com.things.cgomp.system.api.RemoteOperatorService;
import com.things.cgomp.system.api.domain.SysOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * @author things
 * @date 2025/3/3
 */
@Slf4j
@Service
public class OrderCommissionRuleServiceImpl extends ServiceImpl<OrderCommissionRuleMapper, OrderCommissionRule> implements IOrderCommissionRuleService {

    @Resource
    private RemoteOperatorService remoteOperatorService;
    @Resource
    private OrderShareholdersService shareholdersService;

    @Override
    public OrderCommissionRule selectRule(Long ruleId) {
        return baseMapper.selectRuleById(ruleId);
    }

    @Override
    @DataScope(orgAlias = "o")
    public List<OrderCommissionRule> selectRuleList(OrderCommissionRule rule) {
        return baseMapper.selectRuleList(rule);
    }

    @Override
    public List<OrderCommissionRule> selectOperatorRuleList(Long operatorId) {
        LambdaQueryWrapperX<OrderCommissionRule> wrapper = new LambdaQueryWrapperX<OrderCommissionRule>()
                .eq(OrderCommissionRule::getOperatorId, operatorId)
                .eq(OrderCommissionRule::getDelFlag, CommonStatus.NOT_DELETED.getCode())
                .eq(OrderCommissionRule::getStatus, CommonStatus.OK.getCode());
        return baseMapper.selectList(wrapper);
    }

    @Override
    public int updateCommissionRule(OrderCommissionRule rule) {
        if (rule.getId() != null) {
            // 根据ID更新
            return baseMapper.updateRule(rule);
        }
        // 无ID更新
        CommissionLevel commissionLevel = CommissionLevel.getByLevel(rule.getLevel());
        if (commissionLevel == null) {
            throw exception(ErrorCodeConstants.COMMISSION_RULE_LEVEL_ILLEGAL);
        }
        if (commissionLevel.isOperatorLevel() && rule.getUserId() == null) {
            // 运营商级别，设置运营商用户ID
            R<SysOperator> operatorR = remoteOperatorService.getOperatorInfoById(rule.getOperatorId(), SecurityConstants.INNER);
            if (R.SUCCESS != operatorR.getCode() || operatorR.getData() == null) {
                log.error("insertCommissionRule 获取运营商异常，message={}", operatorR.getMsg());
                throw exception(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
            }
            rule.setUserId(operatorR.getData().getUserId());
        }
        if (commissionLevel.isShareholdersLevel() && rule.getOperatorId() == null) {
            // 分成者级别，设置运营商ID
            OrderShareholders shareholders = shareholdersService.selectByUserId(rule.getUserId());
            if (shareholders == null) {
                throw exception(ErrorCodeConstants.COMMISSION_RULE_USER_ILLEGAL);
            }
            rule.setOperatorId(shareholders.getOperatorId());
        }
        rule.setCreateBy(SecurityUtils.getUserId());
        rule.setUpdateBy(SecurityUtils.getUserId());
        return baseMapper.saveOrUpdate(rule);
    }

    @Override
    public int updateStatus(OrderCommissionRule rule) {
        OrderCommissionRule updateRule = new OrderCommissionRule()
                .setId(rule.getId())
                .setStatus(rule.getStatus());
        return baseMapper.updateById(updateRule);
    }

    @Override
    public int deleteRule(Long ruleId) {
        OrderCommissionRule rule = new OrderCommissionRule().setId(ruleId).setDelFlag(CommonStatus.DELETED.getCode());
        return baseMapper.updateById(rule);
    }
}
