package com.things.cgomp.app.service;

import com.things.cgomp.app.domain.AppBanners;
import com.baomidou.mybatisplus.extension.service.IService;
import com.things.cgomp.app.domain.dto.AppBannerDTO;

import java.util.List;

/**
* @author 0120
* @description 针对表【app_banners】的数据库操作Service
* @createDate 2025-02-27 13:53:22
*/
public interface AppBannersService  {

    List<AppBanners> getAllActiveBanners();

    void createBanner(AppBannerDTO banner);

    void updateBanner(Long id, AppBannerDTO banner);

    void deleteBanner(Long id);
}
