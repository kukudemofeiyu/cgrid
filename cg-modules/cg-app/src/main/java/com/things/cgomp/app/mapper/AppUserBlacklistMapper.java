package com.things.cgomp.app.mapper;

import com.things.cgomp.app.api.domain.AppUserBlacklist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 0120
* @description 针对表【app_user_blacklist(黑名单表)】的数据库操作Mapper
* @createDate 2025-02-26 14:36:12
* @Entity com.things.cgomp.app.domain.AppUserBlacklist
*/
@Mapper
public interface AppUserBlacklistMapper extends BaseMapper<AppUserBlacklist> {

}




