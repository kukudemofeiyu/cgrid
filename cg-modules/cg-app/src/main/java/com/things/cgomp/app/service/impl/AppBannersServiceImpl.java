package com.things.cgomp.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.app.domain.AppBanners;
import com.things.cgomp.app.domain.dto.AppBannerDTO;
import com.things.cgomp.app.service.AppBannersService;
import com.things.cgomp.app.mapper.AppBannersMapper;
import com.things.cgomp.common.core.utils.bean.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @description 针对表【app_banners】的数据库操作Service实现
*/
@Slf4j
@Service
public class AppBannersServiceImpl implements AppBannersService{
    @Resource
    private AppBannersMapper appBannersMapper;
    @Override
    public List<AppBanners> getAllActiveBanners() {
        return appBannersMapper.selectAllActiveBanners();
    }

    @Override
    public void createBanner(AppBannerDTO banner) {
        AppBanners appBanners = new AppBanners();
        BeanUtils.copyBeanProp(appBanners,banner);
        appBanners.setCreatedAt(new Date());
        appBannersMapper.insert(appBanners);
    }

    @Override
    public void updateBanner(Long id, AppBannerDTO banner) {
        AppBanners appBanners = appBannersMapper.selectById(id);
        BeanUtils.copyBeanProp(appBanners,banner);
        appBannersMapper.updateById(appBanners);

    }

    @Override
    public void deleteBanner(Long id) {
        AppBanners appBanners = appBannersMapper.selectById(id);
        appBanners.setIsActive(0);
        appBannersMapper.updateById(appBanners);
    }
}




