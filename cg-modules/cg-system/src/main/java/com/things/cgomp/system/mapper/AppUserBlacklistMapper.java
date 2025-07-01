package com.things.cgomp.system.mapper;

import com.things.cgomp.app.api.domain.AppUserBlacklist;
import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import com.things.cgomp.common.mybatisplus.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 注册用户黑名单 数据层
 *
 * @author things
 */
@Mapper
public interface AppUserBlacklistMapper extends BaseMapperX<AppUserBlacklist> {

    List<AppUserBlacklist> selectBlackList(AppUserBlacklist blacklist);

    AppUserBlacklist selectBlackListById(Long id);

    default AppUserBlacklist checkUserUnique(Long userId){
        LambdaQueryWrapperX<AppUserBlacklist> wrapper = new LambdaQueryWrapperX<AppUserBlacklist>()
                .eq(AppUserBlacklist::getUserId, userId)
                .orderByDesc(AppUserBlacklist::getUnsealTime)
                .last("limit 1");
        return selectOne(wrapper);
    }
}
