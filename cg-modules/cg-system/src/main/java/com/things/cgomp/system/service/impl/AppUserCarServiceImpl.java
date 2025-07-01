package com.things.cgomp.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.api.domain.AppUserCar;
import com.things.cgomp.common.core.enums.CommonStatus;
import com.things.cgomp.system.mapper.AppUserCarMapper;
import com.things.cgomp.system.service.IAppUserCarService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.things.cgomp.common.core.utils.PageUtils.startPage;

/**
 * 注册用户车辆实现类
 */
@Service
public class AppUserCarServiceImpl extends ServiceImpl<AppUserCarMapper, AppUserCar> implements IAppUserCarService {

    @Override
    public PageInfo<AppUserCar> selectPage(AppUserCar car) {
        startPage();
        car.setBindStatus(CommonStatus.OK.getCode());
        List<AppUserCar> cars = baseMapper.selectCarList(car);
        return new PageInfo<>(cars);
    }

    @Override
    public AppUserCar selectDefaultCar(
            Long userId
    ) {
        return baseMapper.selectDefaultCar(
                userId
        );
    }
}
