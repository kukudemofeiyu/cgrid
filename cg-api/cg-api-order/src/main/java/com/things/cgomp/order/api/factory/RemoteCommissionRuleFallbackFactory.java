package com.things.cgomp.order.api.factory;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.order.api.RemoteCommissionRuleService;
import com.things.cgomp.order.api.dto.CommissionRuleQueryDTO;
import com.things.cgomp.order.api.dto.CommissionRuleUpdateDTO;
import com.things.cgomp.order.api.domain.OrderCommissionRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 分成规则服务降级处理
 *
 * @author things
 */
@Slf4j
@Component
public class RemoteCommissionRuleFallbackFactory implements FallbackFactory<RemoteCommissionRuleService> {

    @Override
    public RemoteCommissionRuleService create(Throwable throwable) {
        log.error("分成规则服务调用失败:{}", throwable.getMessage());
        return new RemoteCommissionRuleService() {

            @Override
            public R<OrderCommissionRule> selectOne(CommissionRuleQueryDTO rule, String source) {
                return R.fail("查询单个分成规则失败:" + throwable.getMessage());
            }

            @Override
            public R<PageInfo<OrderCommissionRule>> selectPage(CommissionRuleQueryDTO rule, String source) {
                return R.fail("分页查询分成规则失败:" + throwable.getMessage());
            }

            @Override
            public R<OrderCommissionRule> selectById(Long ruleId, String source) {
                return R.fail("查询分成规则失败:" + throwable.getMessage());
            }

            @Override
            public R<Integer> updateRule(CommissionRuleUpdateDTO ruleUpdate, String source) {
                return R.fail("修改分成规则失败:" + throwable.getMessage());
            }

            @Override
            public R<Integer> switchCommissionRule(CommissionRuleUpdateDTO ruleUpdate, String source) {
                return R.fail("修改分成规则状态失败:" + throwable.getMessage());
            }

            @Override
            public R<Integer> deleteCommissionRule(CommissionRuleUpdateDTO ruleUpdate, String source) {
                return R.fail("删除分成规则失败:" + throwable.getMessage());
            }
        };
    }
}
