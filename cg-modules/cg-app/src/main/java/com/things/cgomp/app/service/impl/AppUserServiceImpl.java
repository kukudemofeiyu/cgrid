package com.things.cgomp.app.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.api.domain.AppUser;
import com.things.cgomp.app.api.domain.AppUserCar;
import com.things.cgomp.app.domain.dto.UpdateUserDTO;
import com.things.cgomp.app.domain.vo.AppLoginVO;
import com.things.cgomp.app.domain.vo.PaymentTypeDTO;
import com.things.cgomp.app.domain.vo.UserCarInfoVO;
import com.things.cgomp.app.enums.BindStatus;
import com.things.cgomp.app.enums.DefaultStatus;
import com.things.cgomp.app.enums.ErrorCodeConstants;
import com.things.cgomp.app.mapper.AppPaymentTypeMapper;
import com.things.cgomp.app.mapper.AppUserCarMapper;
import com.things.cgomp.app.mapper.AppUserMapper;
import com.things.cgomp.app.service.AppUserService;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.dto.PageDTO;
import com.things.cgomp.common.core.enums.UserAccountType;
import com.things.cgomp.common.core.exception.enums.GlobalErrorCodeConstants;
import com.things.cgomp.common.security.bean.TokenResponse;
import com.things.cgomp.common.security.service.TokenService;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.system.api.RemoteUserAccountService;
import com.things.cgomp.system.api.domain.SysUserAccount;
import com.things.cgomp.system.api.model.LoginUser;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * @author 0120
 * @description 针对表【app_user(注册用户表（微信小程序用户）)】的数据库操作Service实现
 * @createDate 2025-02-26 14:34:16
 */
@Slf4j
@Service
public class AppUserServiceImpl implements AppUserService {
    @Resource
    private AppUserMapper appUserMapper;
    @Autowired
    private WxMaService wxMaService;
    @Autowired
    private AppUserCarMapper appUserCarMapper;
    @Autowired
    private TokenService tokenService;
    @Resource
    private RemoteUserAccountService remoteUserAccountService;
    @Resource
    private AppPaymentTypeMapper appPaymentTypeMapper;

    @Override
    public AppUser findUserInfo() {
        Long userId = SecurityUtils.getUserId();
        if (userId == 0) {
            throw exception(ErrorCodeConstants.USER_NOT_EXIST);
        }
        return appUserMapper.selectById(userId);
    }

    @Override
    public void appletBindMobile(String searchOpenId, String code) {
        try {
            WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(code);
            log.info("用户:{},phoneNoInfo:{}", searchOpenId, JSONObject.toJSONString(phoneNoInfo));
            if (!ObjectUtils.isEmpty(phoneNoInfo)) {
                String phoneNumber = phoneNoInfo.getPhoneNumber();
                AppUser appUser = appUserMapper.findByOpenId(searchOpenId);
                appUser.setMobile(phoneNumber);
                appUserMapper.updateById(appUser);
            } else {
                throw exception(ErrorCodeConstants.PHONE_NOT_EXIST);
            }
        } catch (WxErrorException e) {
            log.error("绑定手机号出错:{}", e);
            throw exception(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public PageInfo<UserCarInfoVO> getCarInfo(PageDTO pageDTO) {
        Long userId = SecurityUtils.getUserId();  // 获取当前用户ID
        if (userId == 0) {
            throw exception(ErrorCodeConstants.USER_NOT_EXIST);
        }
        int currentPage = Math.max(pageDTO.getCurrent(), 1);  // 当前页至少为1
        int pageSize = Math.max(pageDTO.getPageSize(), 1);  // 每页大小至少为1
        PageHelper.startPage(currentPage, pageSize);  // 启动分页
        // 查询用户的车辆列表
        List<AppUserCar> appUserCarList = appUserCarMapper.findCarListByUserId(userId);

        // 创建PageInfo对象用于存储返回的数据
        PageInfo<UserCarInfoVO> pageInfo = new PageInfo<>();

        if (CollectionUtil.isNotEmpty(appUserCarList)) {
            List<UserCarInfoVO> userCarInfoList = appUserCarList.stream()
                    .map(appUserCar -> {
                        UserCarInfoVO userCarInfoVO = new UserCarInfoVO();
                        userCarInfoVO.setId(appUserCar.getId());
                        userCarInfoVO.setLicensePlateNumber(appUserCar.getLicensePlateNumber());
                        userCarInfoVO.setIsDefault(appUserCar.getIsDefault());
                        return userCarInfoVO;
                    })
                    .collect(Collectors.toList());

            pageInfo.setList(userCarInfoList);  // 设置转换后的结果
        } else {
            pageInfo.setList(Collections.emptyList());  // 设置为空列表
        }

        // 设置分页信息
        pageInfo.setTotal(new PageInfo<>(appUserCarList).getTotal());  // 设置总记录数
        pageInfo.setPageNum(pageDTO.getCurrent());  // 当前页码
        pageInfo.setPageSize(pageDTO.getPageSize());  // 每页记录数

        return pageInfo;  // 返回结果
    }

    @Override
    @Transactional
    public void switchCarStatus(String carNumber) {
        Long userId = SecurityUtils.getUserId();
        if (userId == 0) {
            throw exception(ErrorCodeConstants.USER_NOT_EXIST);
        }
        //判断当前车牌是否已添加
        AppUserCar appUserCar = appUserCarMapper.findByUserIdAndCarNumber(userId, carNumber);
        if (appUserCar == null) {
            throw exception(ErrorCodeConstants.CAR_NOT_EXIST);
        }
        //更新当前用户默认车辆为非默认
        appUserCarMapper.updateDefaultCar(userId);
        //设置当前车辆为默认
        appUserCar.setIsDefault(DefaultStatus.DEFAULT.getCode());
        appUserCarMapper.updateById(appUserCar);


    }

    @Override
    public AppLoginVO appletWeChatLogin(String code) {
        try {
            WxMaJscode2SessionResult sessionInfo = checkAppUser(code);
            String openid = sessionInfo.getOpenid();
            AppUser loginAppUser = appUserMapper.findByOpenId(openid);
            if (ObjectUtils.isEmpty(loginAppUser)) {
                throw exception(ErrorCodeConstants.USER_NOT_EXIST);
            }
            if (StringUtils.isEmpty(loginAppUser.getMobile())) {
                throw exception(ErrorCodeConstants.PHONE_NOT_EXIST);
            }
            //创建返回结构体（普通登录用户无手机号 需返回错误 让前端调用另一个手机登录接口进行绑定手机号）
            //创建token
            LoginUser loginUser = new LoginUser();
            loginUser.setUserid(loginAppUser.getUserId());
            loginUser.setUsername(loginAppUser.getWxOpenId());
            TokenResponse token = tokenService.createAppToken(loginUser);
            AppLoginVO appLoginVO = new AppLoginVO(token);
            appLoginVO.setUser(loginAppUser);
            return appLoginVO;

        } catch (WxErrorException e) {
            log.error("appletWeChatLogin登录出错:{}", e);
            throw exception(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public AppLoginVO phoneLogin(String code, String phoneCode) {
        try {
            //获取手机号
            WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(phoneCode);
            if (ObjectUtils.isEmpty(phoneNoInfo)) {
                throw exception(ErrorCodeConstants.PHONE_NOT_EXIST);
            }
            String phoneNumber = phoneNoInfo.getPhoneNumber();
            //检查手机号是否已经被绑定了
            AppUser appUser = appUserMapper.findByMobile(phoneNumber);
            WxMaJscode2SessionResult sessionInfo = checkAppUser(code);
            String openid = sessionInfo.getOpenid();
            String unionid = sessionInfo.getUnionid();
            if (ObjectUtils.isEmpty(appUser)) {
                //注册用户
                AppUser newUser = new AppUser();
                newUser.setMobile(phoneNumber);
                newUser.setWxOpenId(openid);
                newUser.setCreateTime(new Date());
                newUser.setNickName(phoneNumber);

                if (!StringUtils.isEmpty(sessionInfo.getUnionid())) {
                    newUser.setWxUnionId(unionid);
                }
                appUserMapper.insert(newUser);
            } else {
                //更新用户信息
                appUser.setWxOpenId(openid);
                appUser.setWxUnionId(unionid);
                appUserMapper.updateById(appUser);
            }
            //绑定手机号
            AppUser loginAppUser = appUserMapper.findByOpenId(openid);
            if (StringUtils.isEmpty(loginAppUser.getMobile())) {
                throw exception(ErrorCodeConstants.PHONE_NOT_EXIST);
            }
            //判断用户是否已存在
            Boolean isExist = judgeUserAccount(loginAppUser.getUserId());
            if (!isExist) {
                //注册用户余额账户
                saveUserAccount(loginAppUser.getUserId());
            }
            //创建返回结构体
            //创建token
            LoginUser loginUser = new LoginUser();
            loginUser.setUserid(loginAppUser.getUserId());
            loginUser.setUsername(loginAppUser.getWxOpenId());
            TokenResponse token = tokenService.createAppToken(loginUser);
            AppLoginVO appLoginVO = new AppLoginVO(token);
            appLoginVO.setUser(loginAppUser);
            return appLoginVO;
        } catch (WxErrorException e) {
            log.error("phoneLogin登录出错:{}", e);
            throw exception(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public BigDecimal balance() {
        Long userId = SecurityUtils.getUserId();
        if (userId == 0) {
            throw exception(ErrorCodeConstants.USER_NOT_EXIST);
        }
//        Long userId = 24L;
        R<SysUserAccount> userInfo = remoteUserAccountService.getUserInfo(userId, UserAccountType.APP.getCode(), SecurityConstants.INNER);
        if (Objects.isNull(userInfo) || 200 != userInfo.getCode()) {
            log.error("请求cg-api远程调用remoteUserAccountService服务，getUserInfo接口出错：{}", userInfo.getMsg());
            throw exception(GlobalErrorCodeConstants.NETWORK_ERROR, userInfo.getMsg());
        }
        if (Objects.isNull(userInfo.getData())) {
            //用户余额账户不存在
            throw exception(ErrorCodeConstants.USER_ACCOUNT_NOT_EXIST);
        }
        //获取用户余额保留两位小数
        BigDecimal bigDecimal = userInfo.getData().getBalance().setScale(2, RoundingMode.HALF_UP);
        return bigDecimal;
    }

    @Override
    public List<PaymentTypeDTO> selectPaymentTypeList() {
        Long userId = SecurityUtils.getUserId();
//        Long userId = 1L;
        List<PaymentTypeDTO> paymentTypeList = appPaymentTypeMapper.selectPaymentTypeList();
        //查询用户默认支付方式
        AppUser appUser = appUserMapper.selectById(userId);
        if (Objects.isNull(appUser)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXIST);
        }
        Long paymentTypeId = appUser.getPaymentTypeId();
        if (Objects.nonNull(paymentTypeId)) {
            for (PaymentTypeDTO paymentTypeDTO : paymentTypeList) {
                if (paymentTypeDTO.getId().equals(paymentTypeId)) {
                    paymentTypeDTO.setIsDefault(1);
                } else {
                    paymentTypeDTO.setIsDefault(0);
                }
            }
        }
        return paymentTypeList;
    }

    @Override
    public void updatePaymentType(Long id) {
        Long userId = SecurityUtils.getUserId();
//        Long userId = 1L;
        //查询用户默认支付方式
        AppUser appUser = appUserMapper.selectById(userId);
        if (Objects.isNull(appUser)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXIST);
        }
        Long paymentTypeId = appUser.getPaymentTypeId();
        if (Objects.isNull(paymentTypeId) || !paymentTypeId.equals(id)) {
            appUser.setPaymentTypeId(id);
            appUserMapper.updateById(appUser);
        }
    }

    @Override
    public AppUser updateUserInfo(UpdateUserDTO updateUserDTO) {
        Long userId = SecurityUtils.getUserId();
//        userId =1L;
        AppUser appUser = appUserMapper.selectById(userId);
        appUser.setAvatar(updateUserDTO.getAvatar());
        appUser.setNickName(updateUserDTO.getNickName());
        if (StringUtils.isNotBlank(updateUserDTO.getMobile())){
            appUser.setMobile(updateUserDTO.getMobile());
        }
        appUserMapper.updateById(appUser);
        return appUser;
    }

    @Override
    public AppUserCar findDefaultCarByUserId() {
        return appUserCarMapper.findDefaultCarByUserId(SecurityUtils.getUserId());
    }

    @Override
    public String getWxPhone(String phoneCode) {
        WxMaPhoneNumberInfo phoneNoInfo = null;
        try {
            phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(phoneCode);
        } catch (WxErrorException e) {
            log.error("根据code获取手机号出错:{}", e);
            throw exception(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
        if (ObjectUtils.isEmpty(phoneNoInfo)) {
            throw exception(ErrorCodeConstants.PHONE_NOT_EXIST);
        }
        return phoneNoInfo.getPhoneNumber();
    }

    @Override
    public String getToken(String userId) {
        AppUser appUser = appUserMapper.selectById(userId);
        //创建token
        LoginUser loginUser = new LoginUser();
        loginUser.setUserid(appUser.getUserId());
        loginUser.setUsername(appUser.getWxOpenId());
        TokenResponse token = tokenService.createAppToken(loginUser);
        return token.getAccess_token();
    }

    private Boolean judgeUserAccount(Long userId) {
        //远程查询用户余额账户
        R<SysUserAccount> userAccount = remoteUserAccountService.getUserInfo(userId, UserAccountType.APP.getCode(), SecurityConstants.INNER);
        if (userAccount == null || 200 != userAccount.getCode()) {
            log.error("远程调用查询余额账户失败{}", userAccount);
            throw exception(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
        if (userAccount.getData() == null) {
            return false;
        }
        return true;
    }

    private void saveUserAccount(Long userId) {
        SysUserAccount userAccountDTO = new SysUserAccount();
        userAccountDTO.setUserId(userId);
        userAccountDTO.setBalance(BigDecimal.ZERO);
        userAccountDTO.setType(UserAccountType.APP.getCode());
        userAccountDTO.setUpdateTime(new Date());
        userAccountDTO.setStatus(1);
        R<Boolean> booleanR = null;
        try {
            booleanR = remoteUserAccountService.saveAccount(userAccountDTO, SecurityConstants.INNER);
        } catch (Exception e) {
            throw exception(ErrorCodeConstants.USER_ACCOUNT_SAVE_ERROR);
        }
        if (booleanR == null || 200 != booleanR.getCode()) {
            log.error("远程调用余额账户注册失败{}", booleanR);
            throw exception(ErrorCodeConstants.USER_ACCOUNT_SAVE_ERROR);
        }
    }

    private WxMaJscode2SessionResult checkAppUser(String code) throws WxErrorException {
        WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService().getSessionInfo(code);
        log.info("sessionInfo:{}", JSONObject.toJSONString(sessionInfo));
        if (ObjectUtils.isEmpty(sessionInfo)) {
            throw exception(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
        return sessionInfo;
    }

    @Override
    public void appletBindCar(String carNumber) {
        Long userId = SecurityUtils.getUserId();
        if (userId == 0) {
            throw exception(ErrorCodeConstants.USER_NOT_EXIST);
        }
        //判断当前车牌是否已添加
        AppUserCar appUserCar = appUserCarMapper.findByUserIdAndCarNumber(userId, carNumber);
        if (appUserCar == null) {
            //查找是否为第一辆添加的车
            List<AppUserCar> appUserCarList = appUserCarMapper.findCarListByUserId(userId);
            //直接添加
            appUserCar = new AppUserCar();
            appUserCar.setUserId(userId);
            appUserCar.setLicensePlateNumber(carNumber);
            appUserCar.setBindTime(new Date());
            appUserCar.setBindStatus(BindStatus.BOUND.getCode());
            if (CollectionUtil.isEmpty(appUserCarList)) {
                //第一辆车默认设置为默认
                appUserCar.setIsDefault(DefaultStatus.DEFAULT.getCode());
            }

            appUserCarMapper.insert(appUserCar);
        } else {
            //判断是否解绑
            Integer bindStatus = appUserCar.getBindStatus();
            if (BindStatus.UNBOUND.getCode() == bindStatus) {
                appUserCar.setBindStatus(BindStatus.BOUND.getCode());
                //查找是否为第一辆添加的车
                List<AppUserCar> appUserCarList = appUserCarMapper.findCarListByUserId(userId);
                if (CollectionUtil.isEmpty(appUserCarList)) {
                    //第一辆车默认设置为默认
                    appUserCar.setIsDefault(DefaultStatus.DEFAULT.getCode());
                }

                appUserCarMapper.updateById(appUserCar);
            } else {
                throw exception(ErrorCodeConstants.CAR_NUMBER_EXIST);
            }

        }
    }

    @Override
    public void appletUnbindCar(Long userCarId) {
        //判断当前车牌是否已添加
        AppUserCar appUserCar = appUserCarMapper.selectById(userCarId);
        if (appUserCar == null) {
            throw exception(ErrorCodeConstants.CAR_NOT_EXIST);
        }
        appUserCar.setBindStatus(BindStatus.UNBOUND.getCode());
        appUserCar.setIsDefault(DefaultStatus.NON_DEFAULT.getCode());
        appUserCarMapper.updateById(appUserCar);
        //将最新一辆车作为默认车辆
        List<AppUserCar> carListByUserId = appUserCarMapper.findCarListByUserId(appUserCar.getUserId());
        if (CollectionUtil.isNotEmpty(carListByUserId)) {
            AppUserCar appUserCar1 = carListByUserId.get(0);
            appUserCar1.setIsDefault(DefaultStatus.DEFAULT.getCode());
            appUserCarMapper.updateById(appUserCar1);
        }
    }


}




