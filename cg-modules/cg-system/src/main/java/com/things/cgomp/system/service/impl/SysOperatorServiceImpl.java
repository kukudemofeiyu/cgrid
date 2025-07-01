package com.things.cgomp.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.constant.Constants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.enums.SysUserType;
import com.things.cgomp.common.core.enums.UserAccountType;
import com.things.cgomp.common.core.utils.SpringUtils;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.datascope.constants.DataScopeConstant;
import com.things.cgomp.common.record.enums.AmountRecordType;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.order.api.domain.OrderShareholders;
import com.things.cgomp.pay.api.RemoteRuleService;
import com.things.cgomp.pay.api.dto.RuleApiDTO;
import com.things.cgomp.system.api.domain.*;
import com.things.cgomp.system.api.dto.SysOperatorAccountUpdateDTO;
import com.things.cgomp.system.convert.AmountRecordConvert;
import com.things.cgomp.system.convert.SysOperatorConvert;
import com.things.cgomp.system.enums.ErrorCodeConstants;
import com.things.cgomp.system.mapper.SysOperatorMapper;
import com.things.cgomp.system.mapper.SysUserOperatorMapper;
import com.things.cgomp.system.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.things.cgomp.common.core.utils.PageUtils.startPage;
import static com.things.cgomp.system.enums.ErrorCodeConstants.OPERATOR_DEFAULT_FEE_RULE_EMPTY;

/**
 * @author things
 */
@Slf4j
@Service
public class SysOperatorServiceImpl implements ISysOperatorService {

    @Resource
    private ISysUserService userService;
    @Resource
    private ICommissionRuleService commissionRuleService;
    @Resource
    private SysOperatorMapper operatorMapper;
    @Resource
    private ISysConfigService configService;
    @Resource
    private RemoteRuleService remoteRuleService;
    @Resource
    private ISysOrgService orgService;
    @Resource
    private IShareholdersService shareholdersService;
    @Resource
    private ISysSiteEfService siteEfService;
    @Resource
    private SysUserOperatorMapper userOperatorMapper;
    @Resource
    private ISysUserAccountService userAccountService;
    @Resource
    private ISysAmountRecordService amountRecordService;

    @Override
    public PageInfo<SysOperator> selectOperatorPage(SysOperator operator) {
        startPage();
        List<SysOperator> operators = SpringUtils.getAopProxy(this).selectOperatorList(operator);
        return new PageInfo<>(operators);
    }

    @Override
    @DataScope(orgAlias = "op", userOperatorAlias = "uop")
    public List<SysOperator> selectOperatorList(SysOperator operator) {
        return operatorMapper.selectOperatorList(operator);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertOperator(SysOperator operator) {
        SysUser user = SysOperatorConvert.INSTANCE.convertUser(operator);
        if (!userService.checkUserNameUnique(user)) {
            throw exception(ErrorCodeConstants.USER_USERNAME_IS_EXIST, user.getUsername());
        }
        // 新增运营商组织
        SysOrg org = insertOrg(operator);
        // 新增用户
        insertUser(user, org);
        // 新增运营商
        int row = insertOperator(user, operator);
        // 新增计费规则
        insertRule(operator);
        return row;
    }

    private void insertUser(SysUser user, SysOrg org) {
        user.setOrgId(org.getOrgId());
        // 新增用户相关逻辑
        user.setUserType(SysUserType.OPERATOR_ADMIN.getType());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        SpringUtils.getAopProxy(this).insertUser(user);
    }

    private SysOrg insertOrg(SysOperator operator) {
        // 新增运营商的组织默认为顶层组织
        SysOrg org = SysOperatorConvert.INSTANCE.convertOrg(operator);
        orgService.insertOrg(org);
        return org;
    }

    private int insertOperator(SysUser user, SysOperator operator) {
        operator.setCreateBy(SecurityUtils.getUserId());
        operator.setUserId(user.getUserId());
        operator.setEntryDate(new Date());
        operator.setOrgId(user.getOrgId());
        return operatorMapper.insert(operator);
    }

    private void insertRule(SysOperator operator) {
        String ruleConfig = configService.selectConfigByKey(Constants.SYS_CONFIG_FEE_RULE_KEY);
        if (StringUtils.isEmpty(ruleConfig)) {
            throw exception(OPERATOR_DEFAULT_FEE_RULE_EMPTY);
        }
        RuleApiDTO apiReq = buildFeeRule(ruleConfig, operator.getOperatorId(), operator.getName());
        R<Long> respR = remoteRuleService.saveRule(apiReq);
        if (R.SUCCESS != respR.getCode()) {
            log.error("insertOperator saveRule fail, message={}", respR.getMsg());
            throw exception(ErrorCodeConstants.OPERATOR_INSERT_FAIL);
        }
    }

    @Override
    public SysOperator selectOperatorById(Long operatorId) {
        return operatorMapper.selectOperatorById(operatorId);
    }

    @Override
    public SysOperator selectOperatorByUserId(Long userId) {
        return operatorMapper.selectOperatorByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteOperatorById(Long operatorId) {
        SysOperator operator = operatorMapper.selectSimpleOperatorById(operatorId);
        if (operator == null) {
            return 0;
        }
        // 删除用户
        deleteUser(operator.getUserId());
        // 删除组织
        deleteOrg(operator.getOrgId());
        // 删除站点关联
        deleteSiteEf(operator.getUserId(), operator.getOrgId());
        // 删除用户-运营商关联
        deleteOperatorEf(operatorId);
        // 删除运营商分成规则
        deleteOperatorRule(operatorId);
        // 删除计费规则
        deleteFeeRule(operator.getOperatorId());
        return operatorMapper.deleteOperatorById(operatorId);
    }

    private void deleteSiteEf(Long userId, Long operatorOrgId) {
        siteEfService.deleteSiteEf(operatorOrgId, userId);
    }

    private void deleteOperatorEf(Long operatorId) {
        userOperatorMapper.deleteByOperatorId(operatorId);
    }

    private void deleteFeeRule(Long operatorId) {
        RuleApiDTO ruleApiDTO = new RuleApiDTO().setOperatorId(operatorId);
        remoteRuleService.deleteRule(ruleApiDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOperator(SysOperator operator) {
        SysUser user = SysOperatorConvert.INSTANCE.convertUser(operator);
        if (StringUtils.isNotEmpty(user.getMobile()) && !userService.checkPhoneUnique(user)) {
            throw exception(ErrorCodeConstants.USER_MOBILE_IS_EXIST, user.getMobile());
        }
        // 修改用户
        updateUser(user, operator);
        return operatorMapper.updateById(operator);
    }

    private void updateUser(SysUser user, SysOperator operator) {
        if (StringUtils.isNotEmpty(operator.getPassword())) {
            user.setPassword(SecurityUtils.encryptPassword(operator.getPassword()));
            user.setPasswordUpdateTime(new Date());
        }
        // 修改用户信息
        user.setUpdateBy(SecurityUtils.getUserId());
        user.setSiteIds(Collections.singletonList(DataScopeConstant.ALL_SITE));
        updateUser(user);
    }

    @Override
    public int updateStatus(SysOperator operator) {
        SysOperator updateOperator = new SysOperator().setOperatorId(operator.getOperatorId()).setStatus(operator.getStatus());
        return operatorMapper.updateById(updateOperator);
    }

    @Override
    public void checkOperatorDelete(Long operatorId) {
        // 判断是否包含子分成者
        List<OrderShareholders> shareholders = shareholdersService.selectListByOperatorId(operatorId);
        if (!CollectionUtils.isEmpty(shareholders)) {
            throw exception(ErrorCodeConstants.OPERATOR_HAS_CHILD_CAN_NOT_DELETE);
        }
        // 判断是否存在子运营商
        SysOperator operator = operatorMapper.selectSimpleOperatorById(operatorId);
        if (operator == null) {
            return;
        }
        boolean hasChild = orgService.hasChildByOrgId(operator.getOrgId());
        if (hasChild) {
            throw exception(ErrorCodeConstants.OPERATOR_ORG_HAS_CHILD_CAN_NOT_DELETE);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAccount(SysOperatorAccountUpdateDTO updateDTO) {
        SysOperator operator = operatorMapper.selectOperatorById(updateDTO.getOperatorId());
        if (operator == null) {
            log.warn("运营商信息为空，operatorId={}", updateDTO.getOperatorId());
            return false;
        }
        Long userId = operator.getUserId();
        SysUserAccount account;
        if (updateDTO.getAmount().compareTo(BigDecimal.ZERO) >= 0) {
            // 添加余额
            updateDTO.setBusinessType(AmountRecordType.COMMISSION.getType());
            account = userAccountService.addUserBalance(userId, UserAccountType.WEB.getCode(), updateDTO.getAmount());
        } else {
            // 扣除余额
            updateDTO.setBusinessType(AmountRecordType.WITHDRAW.getType());
            account = userAccountService.reduceUserBalance(userId, UserAccountType.WEB.getCode(), updateDTO.getAmount());
        }
        // 添加记录
        updateDTO.setUserId(operator.getUserId());
        SysAmountRecord record = AmountRecordConvert.INSTANCE.convertOperatorRecord(account, updateDTO);
        amountRecordService.insertAmountRecord(record);
        return true;
    }

    private RuleApiDTO buildFeeRule(String ruleConfig, Long operatorId, String operatorName) {
        RuleApiDTO ruleDTO = JSON.parseObject(ruleConfig, RuleApiDTO.class);
        ruleDTO.setOperatorId(operatorId);
        ruleDTO.setName(operatorName + "默认");
        ruleDTO.setOperatorDefault(1);
        ruleDTO.setSysDefault(0);
        return ruleDTO;
    }

    public void insertUser(SysUser user) {
        this.userService.insertUser(user);
    }

    public void updateUser(SysUser user) {
        this.userService.updateUser(user);
    }

    public void deleteUser(Long userId) {
        this.userService.deleteUserById(userId);
    }

    private void deleteOrg(Long orgId) {
        this.orgService.deleteOrgById(orgId);
    }

    public void deleteOperatorRule(Long operatorId) {
        this.commissionRuleService.deleteByOperatorId(operatorId);
    }
}
