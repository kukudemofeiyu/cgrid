package com.things.cgomp.pay.enums;


import com.things.cgomp.common.core.exception.ErrorCode;

/**
 * PAY 错误码枚举类
 *
 * 支付 模块，使用 1-007-000-000 段
 */
public interface ErrorCodeConstants {

    ErrorCode MODEL_ID_USER_UP = new ErrorCode(1_007_000_001, "模型编号已用完");

    ErrorCode OPERATOR_DEFAULT_RULE_ALREADY_EXIST = new ErrorCode(1_007_000_002, "运营商默认规则已存在");

    ErrorCode PLATFORM_DEFAULT_RULE_ALREADY_EXIST = new ErrorCode(1_007_000_003, "平台默认规则已存在");

    ErrorCode RULE_NOT_FOUND = new ErrorCode(1_007_000_004, "规则未找到");

    ErrorCode UPDATE_DEVICE_RULE_FAIL = new ErrorCode(1_007_000_005, "批量修改设备规则失败");

    ErrorCode  TEMPLATE_HAS_BEEN_ASSOCIATED_ACTIVITY = new ErrorCode(1_007_000_006, "折扣模板已关联活动");

    ErrorCode VERSION_NUMBER_CONFLICT = new ErrorCode(1_007_000_007, "版本号冲突");

    ErrorCode COUPON_TEMPLATE_NOT_FOUND = new ErrorCode(1_007_000_008, "优惠券模板未找到");

    ErrorCode  DISCOUNT_TEMPLATE_DATA_INCORRECT = new ErrorCode(1_007_000_009, "折扣模板数据错误");

    ErrorCode  DISCOUNT_TEMPLATE_BEEN_REMOVE = new ErrorCode(1_007_000_010, "折扣模板已被删除");

    ErrorCode  DISCOUNT_TEMPLATE_TIME_INTERLEAVED = new ErrorCode(1_007_000_011, "折扣模板时间交叉");

    ErrorCode  ACTIVITY_TIME_CONFLICT = new ErrorCode(1_007_000_012, "活动时间冲突");

}
