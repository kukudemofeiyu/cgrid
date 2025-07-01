package com.things.cgomp.pay.api.factory;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.pay.api.RemoteRuleService;
import com.things.cgomp.pay.api.dto.RuleApiDTO;
import com.things.cgomp.device.api.dto.RuleDTO;
import com.things.cgomp.pay.api.dto.RuleSimpleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 计费策略服务降级处理
 *
 * @author things
 */
@Slf4j
@Component
public class RemoteRuleFallbackFactory implements FallbackFactory<RemoteRuleService> {

    @Override
    public RemoteRuleService create(Throwable throwable) {
        log.error("计费策略服务调用失败:{}", throwable.getMessage());
        return new RemoteRuleService() {
            @Override
            public R<Long> saveRule(RuleApiDTO ruleApiDTO) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<Integer> deleteRule(RuleApiDTO ruleApiDTO) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<RuleDTO> selectRule(Long id) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<RuleDTO> selectRule(
                    Long id,
                    Integer modelId
            ) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<RuleDTO> selectDefaultRule(Long operatorId) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<List<RuleDTO>> selectRules(List<Long> ids) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<RuleSimpleDTO> getSimpleRule(Long id) {
                return R.fail(throwable.getMessage());
            }
        };
    }
}
