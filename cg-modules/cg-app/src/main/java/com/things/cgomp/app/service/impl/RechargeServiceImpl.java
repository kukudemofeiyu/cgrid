package com.things.cgomp.app.service.impl;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.api.domain.AppRechargeTrendData;
import com.things.cgomp.app.api.domain.AppUser;
import com.things.cgomp.app.api.domain.AppRechargeOrder;
import com.things.cgomp.app.domain.dto.WithdrawOrRechargeDTO;
import com.things.cgomp.app.enums.ErrorCodeConstants;
import com.things.cgomp.app.api.enums.OrderSource;
import com.things.cgomp.app.enums.OrderStatus;
import com.things.cgomp.app.enums.PurposeEnum;
import com.things.cgomp.app.mapper.AppRechargeOrderMapper;
import com.things.cgomp.app.service.AppUserService;
import com.things.cgomp.app.service.RechargeService;
import com.things.cgomp.app.service.WechatPayService;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.enums.UserAccountType;
import com.things.cgomp.common.core.exception.enums.GlobalErrorCodeConstants;
import com.things.cgomp.common.core.utils.DateUtils;
import com.things.cgomp.common.core.web.domain.TrendQueryDTO;
import com.things.cgomp.common.record.enums.RecordModule;
import com.things.cgomp.common.record.enums.RecordStatus;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.system.api.RemoteAppUserRechargeService;
import com.things.cgomp.system.api.RemoteRecordService;
import com.things.cgomp.system.api.RemoteUserAccountService;
import com.things.cgomp.system.api.domain.SysUserAccount;
import com.things.cgomp.system.api.domain.SysWithdrawRecord;
import com.things.cgomp.system.api.dto.AppRechargeDTO;
import com.things.cgomp.system.api.dto.AppRechargeRecordDTO;
import com.things.cgomp.system.api.dto.AppRefundRecordDTO;
import com.things.cgomp.system.api.vo.AppRechargeRecordVO;
import com.things.cgomp.system.api.vo.AppRefundRecordVO;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.model.Refund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.things.cgomp.common.core.utils.StatisticsUtils.buildQueryParam;

@Slf4j
@Service
public class RechargeServiceImpl implements RechargeService {
    @Autowired(required = false)
    private WechatPayService wechatPayService;
    @Resource
    private RemoteUserAccountService remoteUserAccountService;
    @Resource
    private AppRechargeOrderMapper rechargeOrderMapper;
    @Resource
    private AppUserService appUserService;
    @Autowired(required = false)
    private RemoteAppUserRechargeService rechargeService;
    @Resource
    private RemoteRecordService remoteRecordService;
    @Override
    public PrepayWithRequestPaymentResponse wechatRecharge(String orderNo, WithdrawOrRechargeDTO rechargeDTO) {
        AppRechargeOrder appRechargeOrder = rechargeOrderMapper.selectById(orderNo);
        if (Objects.nonNull(appRechargeOrder)) {
            log.info("订单已处理，直接返回结果，orderNo={}", orderNo);
            throw exception(ErrorCodeConstants.ORDER_EXIST);
        }
        AppUser userInfo = appUserService.findUserInfo();
        if (Objects.isNull(userInfo)){
            throw exception(ErrorCodeConstants.USER_NOT_EXIST);
        }
         // 判断当前用户是否拥有账户信息
        Long userId = userInfo.getUserId();
        R<SysUserAccount> userAccountR = remoteUserAccountService.getUserInfo(userId, UserAccountType.APP.getCode(), SecurityConstants.INNER);
        if (Objects.isNull(userAccountR) || 200 != userAccountR.getCode()) {
            log.error("获取用户信息失败：{}", userAccountR.getMsg());
            throw exception(GlobalErrorCodeConstants.NETWORK_ERROR);
        }
        if (Objects.isNull(userAccountR.getData())) {
            // 用户余额账户不存在
            throw exception(ErrorCodeConstants.USER_ACCOUNT_NOT_EXIST);
        }
      // 创建微信支付预支付订单
        String openId = userInfo.getWxOpenId();
        BigDecimal amount = rechargeDTO.getAmount();
        int amountInCents = amount.multiply(new BigDecimal(100)).intValue(); // 将元转换为分
        PrepayWithRequestPaymentResponse prepayResponse = wechatPayService.createPrepayOrder(openId, amountInCents, orderNo, "微信充值",PurposeEnum.RECHARGE);
        if (Objects.isNull(prepayResponse)) {
            log.error("微信支付预支付订单创建失败");
            throw exception(ErrorCodeConstants.WECHAT_PAY_PREPAY_ORDER_CREATE_FAIL);
        }
        String prepayId = prepayResponse.getPackageVal().split("=|&")[1];
        //创建第三方待支付订单
        this.createOrder(orderNo,prepayId, amount, OrderSource.WECHAT.getCode());
        return prepayResponse;
    }

    private void createOrder(String orderNo, String prepayId, BigDecimal amount, Integer payType) {
        //获取用户ID
        Long userId = SecurityUtils.getUserId();
        if (userId ==0){
            throw exception(ErrorCodeConstants.USER_NOT_EXIST);
        }
        //根据规则生成唯一订单号
        AppRechargeOrder appRechargeOrder = new AppRechargeOrder();
        appRechargeOrder.setAppid(wechatPayService.getAppId());
        appRechargeOrder.setMchid(wechatPayService.getMchId());
        appRechargeOrder.setId(orderNo);
        appRechargeOrder.setUserId(userId);
        appRechargeOrder.setType(payType);
        appRechargeOrder.setAmount(amount);
        appRechargeOrder.setPayAmount(amount);
        appRechargeOrder.setDiscountAmount(BigDecimal.ZERO);
        appRechargeOrder.setThirdPartyOrderId(prepayId);
        rechargeOrderMapper.insert(appRechargeOrder);
    }

    @Override
    public Integer wechatOrderQuery(String orderNo) {
        //查找订单信息
        AppRechargeOrder orderOld = rechargeOrderMapper.selectOrderByOrderNo(orderNo);
        if (Objects.isNull(orderOld)){
            throw exception(ErrorCodeConstants.ORDER_NOT_EXIST);
        }
        if (orderOld.getStatus()== OrderStatus.PENDING.getCode()){
           //轮询微信订单状态
            Transaction transaction = this.OrderQueryByWechat(orderOld.getId());
            if (Objects.nonNull(transaction)){
                Transaction.TradeStateEnum tradeState = transaction.getTradeState();
                //支付成功
                if (Transaction.TradeStateEnum.SUCCESS.equals(tradeState)){
                    this.success(orderOld,transaction);
                }else if (Transaction.TradeStateEnum.CLOSED.equals(tradeState)){
                    //支付失败
                    orderOld.setStatus(OrderStatus.FAILED.getCode());
                    orderOld.setUpdateTime(new Date());
                    rechargeOrderMapper.updateStatus(orderOld);
            }
            }
        }
        return orderOld.getStatus();
    }

    @Override
    public void withdraw(WithdrawOrRechargeDTO withdrawDTO) {
        if (SecurityUtils.getUserId() ==0){
            throw exception(ErrorCodeConstants.USER_NOT_EXIST);
        }
        SysWithdrawRecord sysWithdrawRecord = new SysWithdrawRecord()
                .setUserId(SecurityUtils.getUserId())
                .setAmount(withdrawDTO.getAmount())
                .setModule(RecordModule.APP_USER.getModule())
                .setStatus(RecordStatus.ING.getStatus());
        try {
            R<Boolean> booleanR = remoteRecordService.saveWithdrawRecord(sysWithdrawRecord, SecurityConstants.INNER);
            if (booleanR == null || booleanR.getCode() != 200) {
                log.error("提现失败：{}", booleanR != null ? booleanR.getMsg() : "");
                throw exception(GlobalErrorCodeConstants.NETWORK_ERROR);
            }
        } catch (Exception e) {
            log.error("提现失败：{}", e.getMessage());
            throw exception(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public PageInfo<AppRefundRecordVO> selectRefundRecordList(AppRefundRecordDTO req) {
        if (SecurityUtils.getUserId() ==0){
            throw exception(ErrorCodeConstants.USER_NOT_EXIST);
        }
        req.setUserId(SecurityUtils.getUserId());
        R<PageInfo<AppRefundRecordVO>> pageInfoR = remoteRecordService.refundPage(req.getUserId(), req.getStatus()
                , req.getBeginTime(), req.getEndTime(), req.getCurrent(), req.getPageSize(), SecurityConstants.INNER);
        if (pageInfoR == null || pageInfoR.getCode() != 200) {
            log.error("远程查询提现记录分页列表失败;{}", pageInfoR == null ? null : pageInfoR.getMsg());
            throw exception(GlobalErrorCodeConstants.NETWORK_ERROR);
        }
        return pageInfoR.getData();
    }

    @Override
    public PageInfo<AppRechargeRecordVO> appRechargeRecordPage(AppRechargeRecordDTO req) {
        if (SecurityUtils.getUserId() ==0){
            throw exception(ErrorCodeConstants.USER_NOT_EXIST);
        }
        req.setUserId(SecurityUtils.getUserId());
        R<PageInfo<AppRechargeRecordVO>> pageInfoR = remoteRecordService.appRechargeRecordPage(req.getUserId(),
                req.getBeginTime(), req.getEndTime(), req.getCurrent(), req.getPageSize(), SecurityConstants.INNER);
        if (pageInfoR == null || pageInfoR.getCode() != 200) {
            log.error("远程查询充值记录分页列表失败;{}", pageInfoR == null ? null : pageInfoR.getMsg());
            throw exception(GlobalErrorCodeConstants.NETWORK_ERROR);
        }
        return pageInfoR.getData();
    }

    @Override
    public Transaction OrderQueryByWechat(String orderNo) {
        return wechatPayService.OrderQueryByWechat(orderNo);
    }

    @Override
    public Boolean refund(String OrderNo) {
        AppRechargeOrder orderOld = rechargeOrderMapper.selectById(OrderNo);
        Boolean refunded = wechatPayService.refunded(orderOld);
        if (refunded){
            orderOld.setStatus(OrderStatus.REFUNDED.getCode());
            rechargeOrderMapper.updateStatus(orderOld);
        }
        return refunded;
    }

    @Override
    public Refund refundQueryByWechat(String orderNo) {
        return  wechatPayService.queryRefund(orderNo);
    }

    /**
     * 支付成功
     */
    public  void success( AppRechargeOrder orderOld,Transaction transaction) {
        try {
            // 支付完成时间
            Date payTime = DateUtils.parseDate(transaction.getSuccessTime(), "yyyy-MM-dd'T'HH:mm:ss+08:00");
            // 校验订单信息 & 支付结果 & 订单有效期止日期
            if (orderOld != null && orderOld.getStatus() == 0) {
                // 构建修改对象
                orderOld.setStatus(1);
                orderOld.setPayTime(payTime);
                int isSuccess = rechargeOrderMapper.updateStatus(orderOld);
                if (isSuccess == 1) {
                    //远程修改充值订单
                    AppRechargeDTO appRechargeDTO = new AppRechargeDTO();
                    appRechargeDTO.setUserId(orderOld.getUserId());
                    appRechargeDTO.setAmount(orderOld.getAmount());
                    appRechargeDTO.setOrderNo(orderOld.getId());
                    R<Integer> integerR = rechargeService.appRecharge(appRechargeDTO, SecurityConstants.INNER);
                    if ( integerR == null || integerR.getCode() != 200){
                        log.error("远程修改充值订单失败：{}", integerR == null ? null : integerR.getMsg());
                        //重试3次
                        for (int i = 0; i < 3; i++) {
                            R<Integer> integerR1 = rechargeService.appRecharge(appRechargeDTO, SecurityConstants.INNER);
                            if (integerR1 != null && integerR1.getCode() == 200) {
                                log.error("远程修改充值订单重试成功");
                                break;
                            }
                        }
                    }
                } else {
                    log.error("乐观锁冲突，订单更新失败，订单ID: {}", orderOld.getId());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void handleWxpayCallback(String body, HttpServletRequest request) {
        try {
            // 验签、解密并转换成 Transaction
            Transaction transaction = wechatPayService.getTransaction(body, request);
            log.info("支付成功返回参数{}",transaction);
            // 校验交易状态
            if (Transaction.TradeStateEnum.SUCCESS.equals(transaction.getTradeState())) {
                // 支付成功，根据订单编号查询订单信息
                // 1.查询订单信息
                AppRechargeOrder orderOld = rechargeOrderMapper.selectById(transaction.getOutTradeNo());
                if (orderOld == null) {
                    log.error("订单不存在，订单编号：{}", transaction.getOutTradeNo());
                    return;
                }
                // 校验金额
                if (orderOld.getAmount().equals(transaction.getAmount().getTotal())) {
                    // 金额相等 完成支付 更新订单状态
                        //
                        this.success(orderOld,transaction);
                } else {
                    // 金额异常 执行退款
                    Boolean refunded = wechatPayService.refunded(orderOld);
                    if (refunded){
                        orderOld.setStatus(OrderStatus.REFUNDED.getCode());
                        rechargeOrderMapper.updateStatus(orderOld);
                    }
                }
            }
        } catch (ValidationException e) {
            // 签名验证失败，返回 401 UNAUTHORIZED 状态码
            throw exception(GlobalErrorCodeConstants.UNAUTHORIZED);
        }
    }

    @Override
    public List<AppRechargeTrendData> selectRechargeTrendData(String beginDate, String endDate) {
        TrendQueryDTO queryDTO = buildQueryParam(beginDate, endDate);
        return rechargeOrderMapper.selectTrendData(queryDTO);
    }
}
