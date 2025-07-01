package com.things.cgomp.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.api.domain.AppUser;
import com.things.cgomp.system.api.domain.AppUserTrendDateData;
import com.things.cgomp.system.api.dto.AppRechargeDTO;
import com.things.cgomp.system.domain.dto.AppUserRechargeReq;
import com.things.cgomp.system.domain.dto.AppUserRefundReq;
import com.things.cgomp.system.domain.dto.RegisterTransferDTO;

import java.util.List;
import java.util.Map;

/**
 * 注册用户服务类
 */
public interface IAppUserService extends IService<AppUser> {

    void updateFirstChargeStatus(
            Long userId
    );

    Integer selectFirstChargeStatus(
            Long userId
    );

    /**
     * 分页获取注册用户列表
     * @param appUser 请求对象
     * @return PageInfo
     */
    PageInfo<AppUser> selectPage(AppUser appUser);

    /**
     * 获取注册用户列表
     * @return List
     */
    List<AppUser> selectList(AppUser appUser);

    /**
     * 根据手机号码查询注册用户
     * @param mobile 手机号码
     * @return AppUser
     */
    AppUser selectUserByMobile(String mobile);

    /**
     * 注册用户从卡号中转账
     * @param req 请求对象
     * @return boolean
     */
    boolean transferFromCard(RegisterTransferDTO req);

    /**
     * 系统充值
     * @param req 请求对象
     * @return int
     */
    int systemRecharge(AppUserRechargeReq req);

    /**
     * 退款操作
     * @param req 退款请求对象
     * @return int
     */
    int refund(AppUserRefundReq req);

    Integer appRecharge(AppRechargeDTO req);

    Map<Long,AppUser> selectUserMap(
      List<Long> userIds
    );

    List<AppUserTrendDateData> selectTrendDateData(String beginDate, String endDate);
}
