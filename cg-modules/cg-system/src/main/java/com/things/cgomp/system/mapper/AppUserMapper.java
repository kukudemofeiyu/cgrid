package com.things.cgomp.system.mapper;


import com.things.cgomp.app.api.domain.AppUser;
import com.things.cgomp.common.core.web.domain.TrendQueryDTO;
import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import com.things.cgomp.system.api.domain.AppUserTrendDateData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 注册用户 数据层
 *
 * @author things
 */
@Mapper
public interface AppUserMapper extends BaseMapperX<AppUser> {

    List<AppUser> selectUserList(AppUser appUser);

    List<AppUserTrendDateData> selectTrendDateData(TrendQueryDTO queryDTO);
}
