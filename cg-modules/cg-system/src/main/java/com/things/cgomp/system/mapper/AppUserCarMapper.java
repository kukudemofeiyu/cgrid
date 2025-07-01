package com.things.cgomp.system.mapper;

import com.things.cgomp.app.api.domain.AppUserCar;
import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 注册用户车辆 数据层
 *
 * @author things
 */
@Mapper
public interface AppUserCarMapper extends BaseMapperX<AppUserCar> {

    List<AppUserCar> selectCarList(AppUserCar req);

    AppUserCar selectDefaultCar(
            @Param("userId") Long userId
    );
}
