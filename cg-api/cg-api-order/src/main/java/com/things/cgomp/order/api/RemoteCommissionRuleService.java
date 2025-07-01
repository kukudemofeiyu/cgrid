package com.things.cgomp.order.api;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.order.api.dto.CommissionRuleQueryDTO;
import com.things.cgomp.order.api.dto.CommissionRuleUpdateDTO;
import com.things.cgomp.order.api.domain.OrderCommissionRule;
import com.things.cgomp.order.api.factory.RemoteCommissionRuleFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

/**
 * 分成规则服务
 *
 * @author things
 */
@FeignClient(contextId = "remoteCommissionRuleService",
        value = ServiceNameConstants.ORDER_SERVICE,
        //url = "http://localhost:9013",
        fallbackFactory = RemoteCommissionRuleFallbackFactory.class)
public interface RemoteCommissionRuleService {

    /**
     * 查询单个分成规则
     *
     * @return 结果
     */
    @GetMapping(value = "/commissionRule/getOne")
    public R<OrderCommissionRule> selectOne(@SpringQueryMap CommissionRuleQueryDTO rule, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 分页查询分成规则
     *
     * @return 结果
     */
    @GetMapping(value = "/commissionRule/page")
    public R<PageInfo<OrderCommissionRule>> selectPage(@SpringQueryMap CommissionRuleQueryDTO rule, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 分页查询分成规则
     *
     * @return 结果
     */
    @GetMapping(value = "/commissionRule/{ruleId}")
    public R<OrderCommissionRule> selectById(@PathVariable("ruleId") Long ruleId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 设置分成规则
     *
     * @return 结果
     */
    @PutMapping(value = "/commissionRule")
    public R<Integer> updateRule(@RequestBody CommissionRuleUpdateDTO ruleUpdate, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 设置分成规则状态
     *
     * @return 结果
     */
    @PutMapping(value = "/commissionRule/switch")
    public R<Integer> switchCommissionRule(@RequestBody CommissionRuleUpdateDTO ruleUpdate, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 删除分成规则
     *
     * @return 结果
     */
    @DeleteMapping(value = "/commissionRule")
    public R<Integer> deleteCommissionRule(@RequestBody CommissionRuleUpdateDTO ruleUpdate, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
