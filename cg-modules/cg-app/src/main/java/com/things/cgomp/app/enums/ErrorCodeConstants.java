package com.things.cgomp.app.enums;


import com.things.cgomp.common.core.exception.ErrorCode;

/**
 * APP 错误码枚举类
 *
 * 小程序API 模块，使用 1-009-000-000 段
 */
public interface ErrorCodeConstants {
    /**
     * 用户模块
     */
    ErrorCode PHONE_NOT_EXIST = new ErrorCode(1_009_001_001, "用户手机号不存在");
    ErrorCode CAR_NOT_EXIST = new ErrorCode(1_009_001_002, "用户车牌不存在");

    ErrorCode CAR_NUMBER_EXIST = new ErrorCode(1_009_001_003, "用户车牌已添加，无需重复添加");
    ErrorCode USER_NOT_EXIST = new ErrorCode(1_009_001_004, "用户不存在未注册");
    ErrorCode USER_ACCOUNT_SAVE_ERROR = new ErrorCode(1_009_001_005, "余额账户注册失败");;
    ErrorCode USER_ACCOUNT_NOT_EXIST = new ErrorCode(1_009_001_006, "余额账户账户不存在");
    ErrorCode WECHAT_PAY_PREPAY_ORDER_CREATE_FAIL = new ErrorCode(1_009_001_007, "微信创建预支付订单失败");

    ErrorCode ORDER_NOT_EXIST = new ErrorCode(1_009_001_008, "订单不存在");
    ErrorCode ORDER_EXIST = new ErrorCode(1_009_001_009, "订单已存在");

    ErrorCode REFUND_FAILED = new ErrorCode(1_009_001_010, "微信退款失败");
}
