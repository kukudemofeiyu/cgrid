package com.things.cgomp.app.mapper;

import com.things.cgomp.app.domain.AppBanners;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 0120
* @description 针对表【app_banners】的数据库操作Mapper
* @createDate 2025-02-27 13:53:22
* @Entity com.things.cgomp.app.domain.AppBanners
*/
@Mapper
public interface AppBannersMapper extends BaseMapper<AppBanners> {

     List<AppBanners> selectAllActiveBanners();
}




