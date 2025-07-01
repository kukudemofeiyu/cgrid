package com.things.cgomp.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.api.domain.AppUserCar;

/**
 * 注册用户车辆服务类
 */
public interface IAppUserCarService extends IService<AppUserCar> {

    PageInfo<AppUserCar> selectPage(AppUserCar car);

    AppUserCar selectDefaultCar(
            Long userId
    );
}
