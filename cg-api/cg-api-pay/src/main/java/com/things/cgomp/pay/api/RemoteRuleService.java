package com.things.cgomp.pay.api;

import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.pay.api.dto.RuleApiDTO;
import com.things.cgomp.device.api.dto.RuleDTO;
import com.things.cgomp.pay.api.dto.RuleSimpleDTO;
import com.things.cgomp.pay.api.factory.RemoteRuleFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户服务
 *
 * @author things
 */
@FeignClient(
        contextId = "remoteRuleService",
        value = ServiceNameConstants.PAY_DEVICE,
        //          url = "http://localhost:9014",
        fallbackFactory = RemoteRuleFallbackFactory.class
)
public interface RemoteRuleService {

    @PostMapping(value = "/rule/api", name = "新增规则")
    R<Long> saveRule(@RequestBody RuleApiDTO ruleApiDTO);

    @DeleteMapping(value = "/rule/api", name = "删除规则")
    R<Integer> deleteRule(@RequestBody RuleApiDTO ruleApiDTO);

    @GetMapping(value = "/rule", name = "查询规则")
    R<RuleDTO> selectRule(
            @RequestParam(value = "id") Long id);

    @GetMapping(value = "/rule/model", name = "查询规则")
    R<RuleDTO> selectRule(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "modelId") Integer modelId
    );

    @GetMapping(value = "/rule/operatorDefaultRule", name = "查询运营商默认规则")
    R<RuleDTO> selectDefaultRule(
            @RequestParam(value = "operatorId") Long operatorId
    );

    @GetMapping(value = "/rule/listByRules", name = "查询多个规则")
    R<List<RuleDTO>> selectRules(@RequestParam(value = "ids") List<Long> ids);

    @GetMapping(value = "/rule/getSimpleRule", name = "查询单个规则详情")
    R<RuleSimpleDTO> getSimpleRule(@RequestParam(value = "id") Long id);
}
