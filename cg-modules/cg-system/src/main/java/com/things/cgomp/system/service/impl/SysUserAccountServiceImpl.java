package com.things.cgomp.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.common.core.constant.UserConstants;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.system.api.domain.SysUserAccount;
import com.things.cgomp.system.enums.ErrorCodeConstants;
import com.things.cgomp.system.mapper.SysUserAccountMapper;
import com.things.cgomp.system.service.ISysUserAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * @author things
 * @date 2025/3/7
 */
@Service
public class SysUserAccountServiceImpl extends ServiceImpl<SysUserAccountMapper, SysUserAccount> implements ISysUserAccountService {

    @Override
    public SysUserAccount selectByUserIdAndType(Long userId, String accountType) {
        return baseMapper.selectUniqueAccount(userId, accountType);
    }

    @Override
    public boolean checkUserAccountUnique(Long userId, String accountType) {
        SysUserAccount account = selectByUserIdAndType(userId, accountType);
        if (StringUtils.isNotNull(account)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public int saveUserAccount(SysUserAccount userAccount) {
        return baseMapper.insert(userAccount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserAccount addUserBalance(Long userId, String accountType, BigDecimal amount) {
        SysUserAccount account = baseMapper.selectByUserId(userId, accountType);
        if (account == null) {
            throw exception(ErrorCodeConstants.USER_ACCOUNT_IS_NULL);
        }
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setNewBalance(newBalance);
        account.setUpdateTime(new Date());
        int success = baseMapper.updateAccountBalance(account);
        if (success != 1) {
            throw exception(ErrorCodeConstants.USER_ACCOUNT_BALANCE_UPDATE_FAIL);
        }
        return account;
    }

    @Override
    public SysUserAccount reduceUserBalance(Long userId, String accountType, BigDecimal amount) {
        SysUserAccount account = baseMapper.selectByUserId(userId, accountType);
        if (account == null) {
            throw exception(ErrorCodeConstants.USER_ACCOUNT_IS_NULL);
        }
        if (account.getBalance().compareTo(amount) < 0) {
            // 账户余额不足
            throw exception(ErrorCodeConstants.USER_ACCOUNT_BALANCE_NOT_ENOUGH);
        }
        BigDecimal newBalance = account.getBalance().subtract(amount);
        account.setNewBalance(newBalance);
        account.setUpdateTime(new Date());
        int success = baseMapper.updateAccountBalance(account);
        if (success != 1) {
            throw exception(ErrorCodeConstants.USER_ACCOUNT_BALANCE_UPDATE_FAIL);
        }
        return account;
    }
}
