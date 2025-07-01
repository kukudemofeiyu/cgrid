package com.things.cgomp.app.mapper;

import com.things.cgomp.app.api.domain.AppUserCar;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 0120
* @description 针对表【app_user_car(车辆信息表)】的数据库操作Mapper
* @createDate 2025-02-26 14:36:12
* @Entity com.things.cgomp.app.domain.AppUserCar
*/
@Mapper
public interface AppUserCarMapper extends BaseMapper<AppUserCar> {

    AppUserCar findByUserIdAndCarNumber(@Param("userId") Long userId, @Param("carNumber") String carNumber);

    List<AppUserCar> findCarListByUserId(Long userId);

    AppUserCar findDefaultCarByUserId(Long userId);

    void updateDefaultCar(Long userId);
}




