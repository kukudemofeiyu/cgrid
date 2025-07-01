package com.things.cgomp.app.service;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.api.domain.AppUser;
import com.things.cgomp.app.api.domain.AppUserCar;
import com.things.cgomp.app.domain.dto.UpdateUserDTO;
import com.things.cgomp.app.domain.vo.AppLoginVO;
import com.things.cgomp.app.domain.vo.PaymentTypeDTO;
import com.things.cgomp.app.domain.vo.UserCarInfoVO;
import com.things.cgomp.common.core.dto.PageDTO;

import java.math.BigDecimal;
import java.util.List;

/**
* @author 0120
* @description 针对表【app_user(注册用户表（微信小程序用户）)】的数据库操作Service
* @createDate 2025-02-26 14:34:16
*/
public interface AppUserService{

    AppUser findUserInfo();

    void appletBindMobile(String searchOpenId, String code);

    void appletBindCar( String carNumber);

    void appletUnbindCar( Long userCarId);

    PageInfo<UserCarInfoVO> getCarInfo(PageDTO pageDTO);

    void switchCarStatus(String carNumber);

    AppLoginVO appletWeChatLogin(String code);

    AppLoginVO phoneLogin(String code, String phoneCode);

    BigDecimal balance();

    List<PaymentTypeDTO> selectPaymentTypeList();

    void updatePaymentType(Long id);

    AppUser updateUserInfo(UpdateUserDTO updateUserDTO);

    AppUserCar findDefaultCarByUserId();

    String getWxPhone(String phoneCode);

    String getToken(String userId);
}
