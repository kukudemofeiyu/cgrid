package com.things.cgomp.app.config;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Data
@ConfigurationProperties(prefix = "wx.pay")
public class WechatPayConfig {

    /**
     * 应用ID
     */
    public  String appid;

    /**
     * 商户号
     */
    public  String merchantId;

    /**
     * 商户API私钥路径
     */
    public  String privateKeyPath;

    /**
     * 商户证书序列号
     */
    public  String merchantSerialNumber;

    /**
     * 商户APIV3密钥
     */
    public  String apiV3Key;

    /**
     * 通知地址（有效性：1. HTTPS；2. 不允许携带查询串。）
     */
    public  String payUrl;

    public  String rechargeUrl;

    /**
     * 订单支付超时时间/分钟
     */
    public static final Integer ORDER_PAY_TIME_OUT = 1440;



}

