package com.things.cgomp.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.things.cgomp.system.api.domain.SysUserAccount;

import java.math.BigDecimal;

/**
 * @author things
 * @date 2025/3/7
 */
public interface ISysUserAccountService extends IService<SysUserAccount> {

    /**
     * 根据用户ID和类型查询账户
     * @param userId      用户ID
     * @param accountType 账户类型
     * @return SysUserAccount
     */
    SysUserAccount selectByUserIdAndType(Long userId, String accountType);
    /**
     * 校验账号唯一性
     * @param userId      用户ID
     * @param accountType 账号类型
     * @return true/false
     */
    boolean checkUserAccountUnique(Long userId, String accountType);
    /**
     * 添加用户账号
     * @param userAccount 请求对象
     * @return int
     */
    int saveUserAccount(SysUserAccount userAccount);
    /**
     * 添加用户余额
     * @param userId        用户ID
     * @param accountType   账号类型
     * @param amount        金额
     * @return int
     */
    SysUserAccount addUserBalance(Long userId, String accountType, BigDecimal amount);
    /**
     * 扣除用户余额
     * @param userId        用户ID
     * @param accountType   账号类型
     * @param amount        金额
     * @return int
     */
    SysUserAccount reduceUserBalance(Long userId, String accountType, BigDecimal amount);
}
