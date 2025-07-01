package com.things.cgomp.pay.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.mybatisplus.query.LambdaQueryWrapperX;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.device.api.RemoteDeviceService;
import com.things.cgomp.device.api.dto.UpdateChargeGridRulesDTO;
import com.things.cgomp.pay.api.dto.RuleApiDTO;
import com.things.cgomp.device.api.dto.RuleDTO;
import com.things.cgomp.pay.domain.Rule;
import com.things.cgomp.pay.dto.rule.CopyRuleDTO;
import com.things.cgomp.pay.dto.rule.RuleContentDTO;
import com.things.cgomp.pay.dto.rule.RulePageDTO;
import com.things.cgomp.pay.dto.rule.RuleQueryDTO;
import com.things.cgomp.pay.enums.ErrorCodeConstants;
import com.things.cgomp.pay.mapper.RuleMapper;
import com.things.cgomp.pay.service.IRuleService;
import com.things.cgomp.pay.util.RuleModelIdUtils;
import com.things.cgomp.pay.vo.RuleVo;
import com.things.cgomp.pay.vo.rule.SimpleRuleVo;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 收费规则表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-03
 */
@Service
public class RuleServiceImpl extends ServiceImpl<RuleMapper, Rule> implements IRuleService {

    @Resource
    private RemoteDeviceService remoteDeviceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveRule(RuleDTO ruleDTO) {
        if (ruleDTO.getOperatorId() == null) {
            ruleDTO.setOperatorId(SecurityUtils.getOperatorId());
        }
        return saveRule(
                ruleDTO.getOperatorId(),
                null,
                null,
                ruleDTO
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long copyRule(CopyRuleDTO ruleDTO) {
        RuleDTO rule = selectRule(ruleDTO.getId());
        if (rule == null) {
            return null;
        }

        return saveRule(
                ruleDTO.getOperatorId(),
                null,
                null,
                rule
        );
    }

    private Long saveRule(
            Long operatorId,
            Integer operatorDefault,
            Integer sysDefault,
            RuleDTO ruleDTO
    ) {
        Rule rule = insertRule(
                operatorId,
                operatorDefault,
                sysDefault,
                ruleDTO
        );

        ruleDTO.setId(rule.getId());

        return rule.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveRule(RuleApiDTO apiDTO) {
        RuleDTO ruleDTO = new RuleDTO()
                .setName(apiDTO.getName())
                .setFees(apiDTO.getFees())
                .setTimes(apiDTO.getTimes());
        return saveRule(
                apiDTO.getOperatorId(),
                apiDTO.getOperatorDefault(),
                apiDTO.getSysDefault(),
                ruleDTO
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long editRule(RuleDTO ruleDTO) {
        Rule oldRule = baseMapper.selectRule(ruleDTO.getId());
        if(oldRule == null){
            throw new ServiceException(ErrorCodeConstants.RULE_NOT_FOUND);
        }

        RuleContentDTO newContent = new RuleContentDTO()
                .setFees(ruleDTO.getFees())
                .setTimes(ruleDTO.getTimes());

        boolean contentChanged = isContentChanged(
                oldRule,
                newContent
        );

        if(!contentChanged){
            Rule rule = new Rule()
                    .setId(ruleDTO.getId())
                    .setUpdateBy(SecurityUtils.getUserId())
                    .setName(ruleDTO.getName());
            baseMapper.updateRule(rule);
        } else {
            baseMapper.deleteRule(ruleDTO.getId());
            Integer modelId = generateNormalModelId(oldRule.getOperatorId());
            Rule newRule = new Rule()
                    .setId(oldRule.getId())
                    .setContent(JSON.toJSONString(newContent))
                    .setModelId(modelId)
                    .setName(ruleDTO.getName())
                    .setOperatorId(oldRule.getOperatorId())
                    .setOperatorDefault(oldRule.getOperatorDefault())
                    .setSysDefault(oldRule.getSysDefault())
                    .setCreateBy(oldRule.getCreateBy())
                    .setCreateTime(oldRule.getCreateTime())
                    .setUpdateBy(SecurityUtils.getUserId())
                    .setUpdateTime(LocalDateTime.now());
            baseMapper.insert(newRule);

            updateDeviceRules(newRule,  selectRule(newRule));
        }

        return ruleDTO.getId();
    }

    private void updateDeviceRules(Rule rule,RuleDTO ruleDTO) {
        UpdateChargeGridRulesDTO updateChargeGridRules = new UpdateChargeGridRulesDTO()
                .setOldPayRuleId(rule.getId())
                .setNewPayRuleId(rule.getId())
                .setNewPayModelId(rule.getModelId())
                .setRuleDTO(ruleDTO);
        R<?> r = remoteDeviceService.updateRules(updateChargeGridRules);
        if(!r.success()){
            throw new ServiceException(ErrorCodeConstants.UPDATE_DEVICE_RULE_FAIL);
        }
    }

    private boolean isContentChanged(
            Rule oldRule,
            RuleContentDTO newContent
    ) {
        if(oldRule == null){
            throw new ServiceException(ErrorCodeConstants.RULE_NOT_FOUND);
        }

        newContent.format();
        RuleContentDTO oldContent = JSON.parseObject(oldRule.getContent(),RuleContentDTO.class);
        return !newContent.equals(oldContent);
    }

    @Override
    public void deleteRule(Long ruleId) {
        baseMapper.deleteRule(ruleId);
    }

    @Override
    public void deleteRuleByOperatorId(Long operatorId) {
        LambdaQueryWrapperX<Rule> wrapper = new LambdaQueryWrapperX<Rule>()
                .eq(Rule::getOperatorId, operatorId);
        baseMapper.delete(wrapper);
    }

    @Override
    @DataScope(orgAlias = "op", userOperatorAlias = "uop")
    public PageInfo<RuleVo> selectPage(RulePageDTO pageDTO) {
        try (Page<Object> ignored =
                     PageHelper.startPage(
                             pageDTO.getCurrent(),
                             pageDTO.getPageSize()
                     )
        ) {

            List<RuleVo> rules = baseMapper.selectRules(pageDTO);

            return new PageInfo<>(rules);
        }
    }

    @Override
    @DataScope(orgAlias = "op", userOperatorAlias = "uop")
    public List<SimpleRuleVo> selectRules(RuleQueryDTO queryDTO) {
        if (queryDTO.getDeviceId() == null) {
            return baseMapper.selectSimpleRules(queryDTO);
        }
        return baseMapper.selectSimpleRulesByPile(queryDTO);
    }

    @Override
    public List<RuleDTO> selectRules(List<Long> ids) {
        List<RuleDTO> ruleDTOList = new ArrayList<>();
        if (CollectionUtil.isEmpty(ids)){
            return ruleDTOList;
        }

        List<Rule> rules = baseMapper.selectBatchIds(ids);

       return rules.stream()
                .map(this::selectRule)
                .collect(Collectors.toList());
    }

    @Override
    public RuleDTO selectRule(Long id) {
        Rule rule = baseMapper.selectRule(id);
        if(rule == null){
            return null;
        }

        return selectRule(
                rule
        );
    }

    @Override
    public RuleDTO selectRule(
            Long id,
            Integer modelId
    ) {
        if(id == null || modelId == null){
            return null;
        }

        Rule rule = baseMapper.selectModelRule(
                id,
                modelId
        );

        return selectRule(
                rule
        );
    }

    private RuleDTO selectRule(Rule rule) {
        RuleDTO ruleDTO = new RuleDTO()
                .setId(rule.getId())
                .setName(rule.getName())
                .setModelId(rule.getModelId())
                .setUpdateTime(rule.getUpdateTime())
                .setOperatorId(rule.getOperatorId());

        RuleContentDTO contentDTO = JSON.parseObject(rule.getContent(),RuleContentDTO.class);

        if (contentDTO != null) {
            ruleDTO.setFees(contentDTO.getFees())
                    .setTimes(contentDTO.getTimes());
        }

        return ruleDTO;
    }

    @Override
    public RuleDTO selectDefaultRule(Long operatorId) {
        Rule rule = baseMapper.selectOperatorDefaultRule(operatorId);
        if(rule == null){
            return null;
        }

        return selectRule(
                rule
        );
    }

    private Rule insertRule(
            Long operatorId,
            Integer operatorDefault,
            Integer sysDefault,
            RuleDTO ruleDTO
    ) {
        check(
                operatorId,
                operatorDefault,
                sysDefault
        );

        Integer modelId = generateNormalModelId(operatorId);

        RuleContentDTO content = new RuleContentDTO()
                .setFees(ruleDTO.getFees())
                .setTimes(ruleDTO.getTimes());

        Rule rule = new Rule()
                .setOperatorId(operatorId)
                .setCreateBy(SecurityUtils.getUserId())
                .setName(ruleDTO.getName())
                .setModelId(modelId)
                .setOperatorDefault(operatorDefault)
                .setSysDefault(sysDefault)
                .setContent(JSON.toJSONString(content));
        baseMapper.insert(rule);
        return rule;
    }

    private void check(
            Long operatorId,
            Integer operatorDefault,
            Integer sysDefault
    ) {
        if(operatorDefault != null && operatorDefault == 1){
            Rule rule = baseMapper.selectOperatorDefaultRule(operatorId);
            if(rule != null){
                throw new ServiceException(ErrorCodeConstants.OPERATOR_DEFAULT_RULE_ALREADY_EXIST);
            }
        }

        if(sysDefault != null && sysDefault == 1){
            Rule rule = baseMapper.selectSysDefaultRule();
            if(rule != null){
                throw new ServiceException(ErrorCodeConstants.PLATFORM_DEFAULT_RULE_ALREADY_EXIST);
            }
        }
    }

    @Nullable
    private Integer generateNormalModelId(Long operatorId) {
        Set<Integer> modelIds = baseMapper.selectNormalModelIds(
                operatorId
        );

        return RuleModelIdUtils.generateNormalModelId(
                modelIds
        );
    }
}
