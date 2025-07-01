package com.things.cgomp.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.order.mapper.UserAccountMapper;
import com.things.cgomp.order.service.IUserAccountService;
import com.things.cgomp.system.api.domain.SysUserAccount;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户账号表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-16
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, SysUserAccount>
        implements IUserAccountService {

    @Override
    public SysUserAccount selectAccount(
            Long userId,
            String type
    ) {
        return baseMapper.selectAccount(
                userId,
                type
        );
    }
}
