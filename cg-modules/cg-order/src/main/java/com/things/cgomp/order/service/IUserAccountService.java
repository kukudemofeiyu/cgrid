package com.things.cgomp.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.things.cgomp.system.api.domain.SysUserAccount;

/**
 * <p>
 * 用户账号表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-16
 */
public interface IUserAccountService extends IService<SysUserAccount> {

    SysUserAccount selectAccount(
            Long userId,
            String type
    );
}
