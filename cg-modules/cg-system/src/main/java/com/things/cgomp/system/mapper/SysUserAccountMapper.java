package com.things.cgomp.system.mapper;

import com.things.cgomp.common.core.enums.CommonStatus;
import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import com.things.cgomp.common.mybatisplus.query.LambdaQueryWrapperX;
import com.things.cgomp.system.api.domain.SysUserAccount;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;
import java.util.Date;

/**
 * @author things
 * @date 2025/2/27
 */
@Mapper
public interface SysUserAccountMapper extends BaseMapperX<SysUserAccount> {

    default SysUserAccount selectByUserId(Long userId, String accountType){
        LambdaQueryWrapperX<SysUserAccount> wrapper = new LambdaQueryWrapperX<SysUserAccount>()
                .eq(SysUserAccount::getUserId, userId)
                .eq(SysUserAccount::getType, accountType);
        return selectOne(wrapper);
    }

    default SysUserAccount selectUniqueAccount(Long userId, String accountType){
        LambdaQueryWrapperX<SysUserAccount> wrapper = new LambdaQueryWrapperX<SysUserAccount>()
                .eq(SysUserAccount::getUserId, userId)
                .eq(SysUserAccount::getType, accountType)
                .eq(SysUserAccount::getStatus, CommonStatus.OK.getCode());
        return selectOne(wrapper);
    }

    default int disableUserAccount(Long[] userIds) {
        SysUserAccount account = new SysUserAccount().setStatus(CommonStatus.DISABLE.getCode());
        account.setUpdateTime(new Date());
        LambdaQueryWrapperX<SysUserAccount> wrapperX = new LambdaQueryWrapperX<SysUserAccount>().in(SysUserAccount::getUserId, Arrays.asList(userIds));
        return update(account, wrapperX);
    }

    default int disableUserAccountByUserId(Long userId) {
        Long[] userIds = new Long[]{userId};
        return disableUserAccount(userIds);
    }

    int updateAccountBalance(SysUserAccount account);
}
