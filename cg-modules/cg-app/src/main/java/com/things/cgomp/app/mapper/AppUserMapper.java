package com.things.cgomp.app.mapper;

import com.things.cgomp.app.api.domain.AppUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 0120
* @description 针对表【app_user(注册用户表（微信小程序用户）)】的数据库操作Mapper
* @createDate 2025-02-26 14:34:16
* @Entity com.things.cgomp.app.domain.AppUser
*/
@Mapper
public interface AppUserMapper extends BaseMapper<AppUser> {

    AppUser findByOpenId(@Param("openid") String openid);

    AppUser findByMobile(String phoneNumber);
}




