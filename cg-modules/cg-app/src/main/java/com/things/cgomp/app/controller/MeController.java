package com.things.cgomp.app.controller;


import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.api.domain.AppUser;
import com.things.cgomp.app.api.domain.AppUserCar;
import com.things.cgomp.app.domain.dto.UpdateUserDTO;
import com.things.cgomp.app.domain.vo.PaymentTypeDTO;
import com.things.cgomp.app.domain.vo.UserCarInfoVO;
import com.things.cgomp.app.service.AppUserService;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.dto.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/me")
public class MeController {

    @Autowired
    private AppUserService appUserService;


    @GetMapping("/accountInfo")
    public R<AppUser> accountInfo() {
        AppUser user = appUserService.findUserInfo();
        return R.ok(user);
    }

    /**
     * 更新用户信息（昵称和头像）
     */
    @PutMapping("/updateUserInfo")
    public R<AppUser> updateUserInfo(@RequestBody UpdateUserDTO updateUserDTO) {
        AppUser user = appUserService.updateUserInfo(updateUserDTO);
        return R.ok(user);
    }
    /**
     * 根据phoneCode获取微信手机号
     */
    @GetMapping("/getWxPhone")
    public R<String> getWxPhone(@RequestParam("phoneCode") String phoneCode) {
        String phone = appUserService.getWxPhone(phoneCode);
        return R.ok(phone);
    }


    /**
     *
     * @param search_openId
     * @param code
     * @return
     */

    @GetMapping("/appletBindMobile")
    public R appletBindMobile(@RequestParam("search_openId") String search_openId, @RequestParam("code") String code) {
        appUserService.appletBindMobile(search_openId, code);
        return R.ok();
    }

    @GetMapping("/carInfo")
    public R<PageInfo<UserCarInfoVO>> carInfo(PageDTO pageDTO) {
        PageInfo<UserCarInfoVO> userCar = appUserService.getCarInfo(pageDTO);
        return R.ok(userCar);
    }

    /**
     * 获取用户默认车辆
     * @return
     */
    @GetMapping("/defaultCar")
    public R<AppUserCar> findDefaultCarByUserId() {
        AppUserCar appUserCar = appUserService.findDefaultCarByUserId();
        return R.ok(appUserCar);
    }

    /**
     * 切换默认车辆
     * @return
     */
    @GetMapping("switchCarStatus")
    public R switchCarStatus( @RequestParam("carNumber") String carNumber) {
        appUserService.switchCarStatus( carNumber);
        return R.ok();
    }
    /**
     * 绑定车辆
     * @param carNumber
     * @return
     */
    @GetMapping("/appletBindCar")
    public R appletBindCar( @RequestParam("carNumber") String carNumber) {
        appUserService.appletBindCar(carNumber);
        return R.ok();
    }

    /**
     * 解除绑定车
     * @param userCarId
     * @return
     */
    @GetMapping("/appletUnbindCar")
    public R appletUnbindCar( @RequestParam("userCarId") Long userCarId) {
        appUserService.appletUnbindCar(userCarId);
        return R.ok();
    }
    /**
     * 余额查询
     */
    @GetMapping("/balance")
    public R<BigDecimal> balance() {
        return R.ok(appUserService.balance());
    }
    /**
     * 支付方式查询
     */
    @GetMapping("/paymentType")
    public R<List<PaymentTypeDTO>> selectPaymentTypeList() {
        List<PaymentTypeDTO> list =  appUserService.selectPaymentTypeList();
        return R.ok(list);
    }
    /**
     * 更新支付方式
     */
    @GetMapping("/updatePaymentType")
    public R updatePaymentType(@RequestParam("id") Long id) {
        appUserService.updatePaymentType(id);
        return R.ok();
    }
}
