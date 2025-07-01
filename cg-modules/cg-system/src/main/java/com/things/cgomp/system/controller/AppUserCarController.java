package com.things.cgomp.system.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.api.domain.AppUserCar;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.system.service.IAppUserCarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 注册用户车辆管理
 *
 * @author things
 */
@Log(title = "注册用户车辆管理")
@RestController
@RequestMapping("/register/car")
public class AppUserCarController {

    @Resource
    private IAppUserCarService appUserCarService;


    @GetMapping("/page")
    @RequiresPermissions("app:register:car:list")
    public R<PageInfo<AppUserCar>> page(AppUserCar userCar){
        PageInfo<AppUserCar> pageInfo = appUserCarService.selectPage(userCar);
        return R.ok(pageInfo);
    }

    @GetMapping(value = "/default",name = "查询默认车辆")
    public R<AppUserCar> selectDefaultCar(
            @RequestParam Long userId
    ) {
        AppUserCar car = appUserCarService.selectDefaultCar(
                userId
        );
        return R.ok(car);
    }
}
