package com.things.cgomp.app.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.things.cgomp.app.api.domain.AppUser;
import com.things.cgomp.app.api.enums.OrderSource;
import com.things.cgomp.app.domain.AppThirdPayOrder;
import com.things.cgomp.app.domain.dto.OrderPayDTO;
import com.things.cgomp.app.enums.ErrorCodeConstants;
import com.things.cgomp.app.enums.OrderStatus;
import com.things.cgomp.app.enums.PurposeEnum;
import com.things.cgomp.app.service.AppUserService;
import com.things.cgomp.app.service.PayService;
import com.things.cgomp.app.service.ThirdPayOrderService;
import com.things.cgomp.app.service.WechatPayService;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.exception.enums.GlobalErrorCodeConstants;
import com.things.cgomp.common.core.utils.DateUtils;
import com.things.cgomp.common.mq.common.AbstractBody;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.common.QueueMsg;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.message.OrderPaySuccessReqMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import com.things.cgomp.common.mq.producer.IProducer;
import com.things.cgomp.order.api.RemoteOrderService;
import com.things.cgomp.order.api.dto.OrderPaySuccessDTO;
import com.things.cgomp.order.api.dto.PayWalletMoneyDTO;
import com.things.cgomp.order.api.vo.OrderDiscountVo;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.wechat.pay.java.service.payments.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

@Slf4j
@Service
public class WechatPayServiceImpl implements PayService {
    @Autowired(required = false)
    private WechatPayService wechatPayService;
    @Resource
    private ThirdPayOrderService thirdPayOrderService;
    @Resource
    private AppUserService appUserService;

    @Autowired(required = false)
    private RemoteOrderService remoteOrderService;
    @Resource
    private IProducer producer;
    @Override
    public PrepayWithRequestPaymentResponse Pay(OrderPayDTO orderPayDTO) {
        AppUser userInfo = appUserService.findUserInfo();
        if (Objects.isNull(userInfo)){
            throw exception(ErrorCodeConstants.USER_NOT_EXIST);
        }
        Long userId = userInfo.getUserId();
        //生成唯一订单号
        String orderNo = generateOrderNo(userId); // 生成唯一订单号
        AppThirdPayOrder appThirdPayOrder = thirdPayOrderService.selectOrderByOrderNo(orderNo);
        if (Objects.nonNull(appThirdPayOrder)) {
            log.info("订单已处理，orderNo={}", orderNo);
            throw exception(ErrorCodeConstants.ORDER_EXIST);
        }
        //获取订单优惠附加数据
        R<OrderDiscountVo> nonPaymentOrderDiscount = remoteOrderService.getNonPaymentOrderDiscount(orderPayDTO.getIds(), orderPayDTO.getSiteActivityIds(), orderPayDTO.getCouponIds());
      if (nonPaymentOrderDiscount ==null || nonPaymentOrderDiscount.getCode() !=200 ){
          log.error("获取订单优惠附加数据失败:{}",nonPaymentOrderDiscount != null ? nonPaymentOrderDiscount.getMsg() : null);
          throw exception(GlobalErrorCodeConstants.NETWORK_ERROR);
      }
        OrderDiscountVo data = nonPaymentOrderDiscount.getData();
        if (Objects.isNull(data)){
            log.error("获取订单优惠附加数据为空");
            throw exception(GlobalErrorCodeConstants.NETWORK_ERROR);
        }
        // 创建微信支付预支付订单
        BigDecimal amount = data.getPayAmount();
        String openId = userInfo.getWxOpenId();
        int amountInCents = amount.multiply(new BigDecimal(100)).intValue(); // 将元转换为分
        PrepayWithRequestPaymentResponse prepayResponse = wechatPayService.createPrepayOrder(openId, amountInCents, orderNo, "微信支付", PurposeEnum.PAY);
        if (Objects.isNull(prepayResponse)) {
            log.error("微信支付预支付订单创建失败");
            throw exception(ErrorCodeConstants.WECHAT_PAY_PREPAY_ORDER_CREATE_FAIL);
        }
        String prepayId = prepayResponse.getPackageVal().split("=|&")[1];
        //创建第三方待支付订单
        thirdPayOrderService.createOrder(orderNo,prepayId, amountInCents, OrderSource.WECHAT.getCode()
                ,wechatPayService.getAppId(),wechatPayService.getMchId(),data.getConfig(),orderPayDTO.getIds());
        return prepayResponse;
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
                AppThirdPayOrder orderOld  = thirdPayOrderService.selectOrderById(transaction.getOutTradeNo());
                if (orderOld == null) {
                    log.error("订单不存在，订单编号：{}", transaction.getOutTradeNo());
                    return;
                }
                // 校验金额
                if (orderOld.getAmount().equals(transaction.getAmount().getTotal())) {
                    // 金额相等 完成支付 更新订单状态
                    //根据支付用途更新订单状态
                        //支付
                        this.success(orderOld,transaction);
                } else {
                    // 金额异常 执行退款
                    Boolean refunded = wechatPayService.refunded(orderOld);
                    if (refunded){
                        orderOld.setStatus(OrderStatus.REFUNDED.getCode());
                        thirdPayOrderService.updateStatus(orderOld);
                    }
                }
            }
        } catch (ValidationException e) {
            // 签名验证失败，返回 401 UNAUTHORIZED 状态码
            throw exception(GlobalErrorCodeConstants.UNAUTHORIZED);
        }
    }

    @Override
    public Integer wechatOrderQuery(String orderNo) {
        //查找订单信息
        AppThirdPayOrder orderOld = thirdPayOrderService.selectOrderByOrderNo(orderNo);
        if (Objects.isNull(orderOld)){
            throw exception(ErrorCodeConstants.ORDER_NOT_EXIST);
        }
        if (Objects.equals(orderOld.getStatus(), OrderStatus.PENDING.getCode())){
            //轮询微信订单状态
            Transaction transaction = wechatPayService.OrderQueryByWechat(orderOld.getId());
            if (Objects.nonNull(transaction)){
                Transaction.TradeStateEnum tradeState = transaction.getTradeState();
                //支付成功
                if (Transaction.TradeStateEnum.SUCCESS.equals(tradeState)){
                    this.success(orderOld,transaction);
                }else if (Transaction.TradeStateEnum.CLOSED.equals(tradeState)){
                    //支付失败
                    orderOld.setStatus(OrderStatus.FAILED.getCode());
                    orderOld.setUpdateTime(new Date());
                    thirdPayOrderService.updateStatus(orderOld);
                }
            }
        }
        return orderOld.getStatus();
    }

    @Override
    public R<?> walletPay(PayWalletMoneyDTO PayWalletMoneyDTO) {
        R<?> r = remoteOrderService.payWalletMoney(PayWalletMoneyDTO);
        if (r ==null ){
            log.error("钱包支付失败，返回信息为空");
            throw exception(GlobalErrorCodeConstants.NETWORK_ERROR);
        }
        return r;
    }

    @Override
    public Boolean refund(String orderNo) {
        AppThirdPayOrder appThirdPayOrder = thirdPayOrderService.selectOrderById(orderNo);
        Boolean refunded = wechatPayService.refunded(appThirdPayOrder);
        if (refunded){
            appThirdPayOrder.setStatus(OrderStatus.REFUNDED.getCode());
            thirdPayOrderService.updateStatus(appThirdPayOrder);
        }
        return   refunded;
    }

    /**
     * 支付成功
     */
    public  void success( AppThirdPayOrder orderOld,Transaction transaction) {
        try {
            // 支付完成时间
            Date payTime = DateUtils.parseDate(transaction.getSuccessTime(), "yyyy-MM-dd'T'HH:mm:ss+08:00");
            // 校验订单信息 & 支付结果 & 订单有效期止日期
            if (orderOld != null && orderOld.getStatus() == 0) {
                // 构建修改对象
                orderOld.setStatus(1);
                orderOld.setPayTime(payTime);

                boolean isSuccess = thirdPayOrderService.updateStatus(orderOld);
                if (isSuccess) {
                    //远程修改充电订单
                    OrderPaySuccessDTO orderPaySuccessDTO = new OrderPaySuccessDTO()
                            .setOrderIds(JSONUtil.toList(orderOld.getChargeOrderSn(), Long.class))
                            .setPayTime(payTime.getTime())

                            .setConfig(JSONUtil.parseArray(orderOld.getAttach()))
                            .setPayOrderId(orderOld.getId());
                    R<?> r = remoteOrderService.updatePayStatus(
                            orderPaySuccessDTO
                    );
                    if ( r == null || r.getCode() != 200){
                        log.error("远程修改充电订单失败：{}", r == null ? null : r.getMsg());
                        //将消息丢到消息队列中
                        OrderPaySuccessReqMsg orderPaySuccessReqMsg = new OrderPaySuccessReqMsg();
                        orderPaySuccessReqMsg.setOrderIds(JSONUtil.toList(orderOld.getChargeOrderSn(), Long.class));
                        orderPaySuccessReqMsg.setPayTime(payTime.getTime());
                        orderPaySuccessReqMsg.setConfig(JSONUtil.parseArray(orderOld.getAttach()))
                                .setPayOrderId(orderOld.getId());
                        this.sentMessage(orderPaySuccessReqMsg);
                    }
                } else {
                    log.error("订单更新失败，订单ID: {}", orderOld.getId());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void sentMessage(OrderPaySuccessReqMsg reqMsg){
        Metadata metadata = Metadata.builder()
                .txId(UUID.randomUUID().toString())
                .requestId(UUID.randomUUID().toString())
                .eventTime(System.currentTimeMillis())
                .build();
        try {
            QueueMsg<AbstractBody> sendMsg = QueueMsg.builder()
                    .metadata(metadata)
                    .body(reqMsg)
                    .build();
            producer.asyncSend(MQTopics.ORDER, MQTopics.Tag.ORDER_PAY_SUCCESS, sendMsg, new SimpleServiceCallback() {
                @Override
                public void onSuccess(Object msg) {
                    log.info(" orderPaySuccess 消息发送成功, msg={}", msg);
                }

                @Override
                public void onError(Throwable e) {
                    log.error("orderPaySuccess 消息发送失败, ", e);
                    // 发送失败暂时不重新发送
                }
            });
        }catch (Exception e){
            log.error("orderPaySuccess error, reqMsg={}, metadata={}", reqMsg, metadata, e);
            // 处理失败暂时不抛出异常
        }
    }



    /**
     * 生成唯一订单号
     */
    private String generateOrderNo( Long userId) {
        // 格式化用户ID为11位，不足部分前面补零
        String formattedUserId = String.format("%011d", userId);
        // 生成随机数（0到999）
        int randomNum = (int) (Math.random() * 1000);
        // 拼接订单号
        return "PY_WX" + formattedUserId + System.currentTimeMillis() + randomNum;
    }
}
