package com.things.cgomp.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.system.api.domain.SysUserAccount;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户账号表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-03-16
 */
public interface UserAccountMapper extends BaseMapper<SysUserAccount> {

    SysUserAccount selectAccount(
           @Param("userId") Long userId,
           @Param("type") String type
    );
}
