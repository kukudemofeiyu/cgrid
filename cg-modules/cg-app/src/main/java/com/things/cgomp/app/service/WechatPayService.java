package com.things.cgomp.app.service;


import com.things.cgomp.app.config.WechatPayConfig;
import com.things.cgomp.app.api.domain.AppRechargeOrder;
import com.things.cgomp.app.domain.AppThirdPayOrder;
import com.things.cgomp.app.enums.ErrorCodeConstants;
import com.things.cgomp.app.enums.PurposeEnum;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.*;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

@Slf4j
@Configuration
@EnableConfigurationProperties(WechatPayConfig.class)
public class WechatPayService {
    private final JsapiService jsapiService;
    private final WechatPayConfig properties;
    private  Config config;

    public WechatPayService(WechatPayConfig properties) {
        this.properties = properties;
        //根据privateKeyPath从路径中读出文件内容
        String privateKeyContent = null;
        try (InputStream file = this.getClass().getClassLoader().getResourceAsStream(properties.privateKeyPath)) {
            if (file == null) {
                throw new RuntimeException("读取私钥文件失败: 文件未找到");
            }
            // 使用 ByteArrayOutputStream 读取所有字节
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int bytesRead;
            while ((bytesRead = file.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, bytesRead);
            }
            buffer.flush();
            // 将字节数据转换为字符串
            privateKeyContent = buffer.toString(StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            log.error("读取私钥文件失败: {}", properties.privateKeyPath, e);
            throw new RuntimeException("读取私钥文件失败", e);
        }
//        String classPath = System.getProperty("java.class.path");
//        // 是否运行在开发环境
//        boolean isRunningFromIde = classPath.toLowerCase().contains("eclipse") || classPath.toLowerCase().contains("idea");
//        // 开发环境与部署jar包环境 不同获取私钥路径
//        if (isRunningFromIde){
//            properties.privateKeyPath = getClass().getClassLoader().getResource("apiclient_key.pem").getPath();
//        }
            // 初始化配置
         config = new RSAAutoCertificateConfig.Builder()
                .merchantId(properties.merchantId)
//                .privateKeyFromPath(properties.privateKeyPath)
                 .privateKey(privateKeyContent)
                .merchantSerialNumber(properties.merchantSerialNumber)
                .apiV3Key(properties.apiV3Key)
                .build();
        this.jsapiService = new JsapiService.Builder().config(config).build();
    }

    /**
     * 创建微信支付预支付订单
     *
     * @param openId      用户 openId
     * @param amount      充值金额（单位：分）
     * @param orderNo     订单号
     * @param description 订单描述
     * @return 预支付订单信息
     */
    public PrepayWithRequestPaymentResponse createPrepayOrder(String openId, int amount, String orderNo, String description, PurposeEnum purpose) {
        JsapiServiceExtension service = new JsapiServiceExtension.Builder().config(config).build();

        PrepayRequest request = new PrepayRequest();
        // 构造请求参数
        request.setAppid(properties.appid); // 替换为你的 AppId
        request.setMchid(properties.merchantId); // 替换为你的商户号
        request.setDescription(description);
        request.setOutTradeNo(orderNo);
        if (PurposeEnum.PAY.equals(purpose)){
            request.setNotifyUrl(properties.payUrl); // 支付回调地址
        }
        if (PurposeEnum.RECHARGE.equals(purpose)){
            request.setNotifyUrl(properties.rechargeUrl); //充值回调地址
        }


        Amount amountObj = new Amount();
        amountObj.setTotal(amount);
        request.setAmount(amountObj);

        Payer payer = new Payer();
        payer.setOpenid(openId);
        request.setPayer(payer);

        // response包含了调起支付所需的所有参数，可直接用于前端调起支付
        return service.prepayWithRequestPayment(request);
    }
    /**
     * 商户订单号查询订单
     */
    public com.wechat.pay.java.service.payments.model.Transaction OrderQueryByWechat(String orderNo) {
            QueryOrderByOutTradeNoRequest queryOrderByOutTradeNoRequest = new QueryOrderByOutTradeNoRequest();
            queryOrderByOutTradeNoRequest.setMchid(properties.merchantId);
            queryOrderByOutTradeNoRequest.setOutTradeNo(orderNo);
            return jsapiService.queryOrderByOutTradeNo(queryOrderByOutTradeNoRequest);
    }
    /**
     * 执行支付退款
     */
    public  Boolean refunded(AppThirdPayOrder wechatPay) {
        try {
            // 构建退款Service
            RefundService service = new RefundService.Builder().config(config).build();
            // 构建请求对象
            CreateRequest request = new CreateRequest();
            request.setOutTradeNo(wechatPay.getId());
            request.setOutRefundNo(wechatPay.getId());
            // 支付总金额（分）
            long total = wechatPay.getAmount();
            // 设置退款金额
            AmountReq amount = new AmountReq();
            amount.setRefund(total);
            amount.setTotal(total);
            amount.setCurrency("CNY");
            request.setAmount(amount);
            // 请求API申请退款
            Refund refund = service.create(request);
            // 校验退款结果
            if (refund != null && Status.PROCESSING.equals(refund.getStatus())) {
                log.info("微信申请退款成功：{}",wechatPay);
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw exception(ErrorCodeConstants.REFUND_FAILED);
        }
        return false;
    }
    /**
     * 执行充值退款
     */
    public  Boolean refunded(AppRechargeOrder wechatPay) {
        try {
            // 构建退款Service
            RefundService service = new RefundService.Builder().config(config).build();
            // 构建请求对象
            CreateRequest request = new CreateRequest();
            request.setOutTradeNo(wechatPay.getId());
            request.setOutRefundNo(wechatPay.getId());
            // 支付总金额（分）
            BigDecimal amount1 = wechatPay.getAmount();
            long total = amount1.multiply(new BigDecimal(100)).longValue();
            // 设置退款金额
            AmountReq amount = new AmountReq();
            amount.setRefund(total);
            amount.setTotal(total);
            amount.setCurrency("CNY");
            request.setAmount(amount);
            // 请求API申请退款
            Refund refund = service.create(request);
            // 校验退款结果
            if (refund != null && Status.PROCESSING.equals(refund.getStatus())) {
                log.info("微信申请退款成功：{}",wechatPay);
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw exception(ErrorCodeConstants.REFUND_FAILED);
        }
        return false;
    }

    /**
     * 查询退款
     * @return
     */
    public Refund queryRefund(String orderNo) {
        // 构建退款Service
        RefundService service = new RefundService.Builder().config(config).build();
        QueryByOutRefundNoRequest queryByOutRefundNoRequest = new QueryByOutRefundNoRequest();
        queryByOutRefundNoRequest.setOutRefundNo(orderNo);
        return service.queryByOutRefundNo(queryByOutRefundNoRequest);

    }
    public Transaction getTransaction(String body, HttpServletRequest request) {
        // 构造 RequestParam
        com.wechat.pay.java.core.notification.RequestParam requestParam = new RequestParam.Builder()
                // 序列号
                .serialNumber(request.getHeader("Wechatpay-Serial"))
                // 随机数
                .nonce(request.getHeader("Wechatpay-Nonce"))
                // 签名
                .signature(request.getHeader("Wechatpay-Signature"))
                // 时间戳
                .timestamp(request.getHeader("Wechatpay-Timestamp"))
                .body(body)
                .build();
        // 初始化解析器
        NotificationParser parser = new NotificationParser((NotificationConfig) config);
        // 验签、解密并转换成 Transaction
        Transaction transaction = parser.parse(requestParam, Transaction.class);
        return transaction;
    }
    public String getAppId(){
        return properties.appid;
    }
    public String getMchId(){
        return properties.merchantId;
    }
}
