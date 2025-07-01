package com.things.cgomp.app.domain.vo;

import lombok.Data;

@Data
public class WxpayCallbackData {
    private String returnCode;       // 返回状态码
    private String returnMsg;        // 返回信息
    private String appId;            // 应用ID
    private String mchId;            // 商户号
    private String deviceInfo;       // 设备信息
    private String nonceStr;         // 随机字符串
    private String sign;             // 签名
    private String resultCode;       // 业务结果
    private String errCode;          // 错误代码
    private String errCodeDes;       // 错误代码描述
    private String openId;           // 用户标识
    private String isSubscribe;      // 是否关注公众账号
    private String tradeType;        // 交易类型
    private String bankType;         // 付款银行
    private int totalFee;            // 订单金额
    private String feeType;          // 货币种类
    private int cashFee;             // 现金支付金额
    private String cashFeeType;      // 现金支付货币类型
    private String transactionId;    // 微信支付订单号
    private String outTradeNo;       // 商户订单号
    private String attach;           // 附加数据
    private String timeEnd;          // 支付完成时间
}

