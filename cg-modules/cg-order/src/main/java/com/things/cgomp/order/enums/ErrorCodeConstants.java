package com.things.cgomp.order.enums;


import com.things.cgomp.common.core.exception.ErrorCode;

/**
 * ORDER 错误码枚举类
 *
 * 订单 模块，使用 1-006-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== 订单 模块 1-004-000-000 ==========
    ErrorCode ORDER_SERIAL_CODE_GEN_FAIL = new ErrorCode(1_004_000_000, "订单号生成失败");
    ErrorCode ORDER_PROCESS_FAIL = new ErrorCode(1_004_000_001, "订单流程处理失败");

    // ========== 分成规则 模块 1-004-001-000 =========
    ErrorCode COMMISSION_RULE_LEVEL_ILLEGAL = new ErrorCode(1_004_001_000, "非法的分成规则等级");
    ErrorCode COMMISSION_RULE_USER_ILLEGAL = new ErrorCode(1_004_001_001, "非法的用户参数");
    ErrorCode COMMISSION_RULE_ID_NOT_NULL = new ErrorCode(1_004_001_002, "分成规则ID不能为空");
    ErrorCode DEVICE_DOES_NOT_EXIST = new ErrorCode(1_004_001_003, "设备信息不存在");
    ErrorCode NO_ACCOUNTING_RULE_BE_CONFIGURED = new ErrorCode(1_004_001_004, "未配置计费规则");
    ErrorCode ACCOUNTING_RULE_DOES_NOT_EXIST = new ErrorCode(1_004_001_005, "计费规则不存在");
    ErrorCode ORDER_NUMBER_ALREADY_EXISTS = new ErrorCode(1_004_001_006, "订单号已存在");
    ErrorCode INSERTION_TIME_NOT_FOUND = new ErrorCode(1_004_001_007, "插枪时间未找到");
    ErrorCode VERSION_NUMBER_CONFLICT = new ErrorCode(1_004_001_008, "版本号冲突");
    ErrorCode WALLET_NOT_FOUND = new ErrorCode(1_004_001_009, "钱包数据未找到");
    ErrorCode ORDER_STATUS_ERROR = new ErrorCode(1_004_001_010, "订单状态错误");
    ErrorCode RULE_NOT_DELIVERED_TO_DEVICE = new ErrorCode(1_004_001_011, "计费规则未下发至设备");
    ErrorCode FAILED_TO_QUERY_DEVICE_DATA = new ErrorCode(1_004_001_012, "查询设备数据失败");
    ErrorCode FAILED_TO_QUERY_RULE_DATA = new ErrorCode(1_004_001_013, "查询规则数据失败");
    ErrorCode ORDER_NOT_FOUND = new ErrorCode(1_004_001_014, "订单不存在");
    ErrorCode NOT_WALLET_ORDER = new ErrorCode(1_004_001_015, "非账户余额支付订单");
    ErrorCode ORDER_DATA_ERROR = new ErrorCode(1_004_001_016, "订单数据错误");
    ErrorCode ORDER_HAVE_PAID = new ErrorCode(1_004_001_017, "订单已支付");
    ErrorCode WALLET_NOT_SUFFICIENT = new ErrorCode(1_004_001_017, "账户余额不足");
    ErrorCode SITE_ACTIVITY_NOT_FOUND = new ErrorCode(1_004_001_018, "站点活动未找到");
    ErrorCode SITE_ACTIVITY_OVER = new ErrorCode(1_004_001_019, "站点活动已结束");
    ErrorCode NOT_WITHIN_SITE_ACTIVITY_TIME_RANGE = new ErrorCode(1_004_001_020, "未在站点活动时间范围内");
    ErrorCode COUPON_NOT_FOUND = new ErrorCode(1_004_001_021, "优惠券未找到");
    ErrorCode COUPON_HAVE_BEEN_USED = new ErrorCode(1_004_001_022, "优惠券已使用");
    ErrorCode NOT_WITHIN_COUPON_TIME_RANGE = new ErrorCode(1_004_001_023, "优惠券未在有效期范围内");
    ErrorCode COUPON_DATA_ERROR = new ErrorCode(1_004_001_024, "优惠券数据错误");
    ErrorCode COUPON_CONDITIONS_NOT_MET = new ErrorCode(1_004_001_025, "未满足优惠券使用条件");

    // ========== 分成 模块 1-004-002-000 =========
    ErrorCode COMMISSION_UPDATE_ACCOUNT_FAIL = new ErrorCode(1_004_002_000, "账户添加订单分成金额失败");
}
