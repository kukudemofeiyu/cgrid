package com.things.cgomp.device.enums;

import com.things.cgomp.common.core.exception.ErrorCode;

/**
 * DEVICE 错误码枚举类
 *
 * 设备 模块，使用 1-005-000-000 段
 */
public interface ErrorCodeConstants {


    // ========== 充电桩管理 模块 1-005-000-000 ==========
    ErrorCode SN_REPEAT = new ErrorCode(1_005_000_000, "桩编码重复");
    ErrorCode DEVICE_NOT_EXIST = new ErrorCode(1_005_000_001, "桩信息不存在");
    ErrorCode DEVICE_OFFLINE = new ErrorCode(1_005_000_002, "桩通信异常");
    ErrorCode NO_PUSH_CHANNEL_BE_AVAILABLE = new ErrorCode(1_005_000_003, "通信通道异常");
    ErrorCode DEFAULT_OPERATOR_RULE_FAILS_QUERY = new ErrorCode(1_005_000_004, "查询运营商默认规则失败");
    ErrorCode FAILED_TO_GENERATE_ORDER_SN = new ErrorCode(1_005_000_005, "生成订单号失败");
    ErrorCode PORT_NUM_TOO_LARGE = new ErrorCode(1_005_000_006, "枪口数量过大");
    ErrorCode NO_GUN_DETECTED  = new ErrorCode(1_005_000_007, "未检测到枪,请拔掉枪重新插入");
    ErrorCode INSUFFICIENT_BALANCE = new ErrorCode(1_005_000_008, "余额不足,请充值");
    ErrorCode NOT_SET_BILLING_RULES = new ErrorCode(1_005_000_009, "当前桩未设置计费规则");
    ErrorCode HAS_CHARGING_ORDER = new ErrorCode(1_005_000_010, "当前用户存在其他正在充电订单");
    ErrorCode GUN_INSERT_STATUS_CHECK_FAIL = new ErrorCode(1_005_000_011, "插枪状态修改失败");
    ErrorCode FAILED_CREATE_ORDER = new ErrorCode(1_005_000_012, "创建订单失败，停止充电");
    ErrorCode CHARGE_FAIL = new ErrorCode(1_005_000_013, "充电失败");
    ErrorCode PAY_RULE_NOT_FOUND = new ErrorCode(1_005_000_014, "计费规则不存在");
    ErrorCode HAS_PAYING_ORDER = new ErrorCode(1_005_000_015, "当前用户存在待支付订单");
    ErrorCode HAS_PORT_SN = new ErrorCode(1_005_000_016, "枪口编号已存在");
    ErrorCode PLEASE_REINSERT_THE_GUN = new ErrorCode(1_005_000_017, "请拔掉枪重新插入");





    // ========== 站点管理 模块 1-005-001-000 ==========
    ErrorCode SITE_COMMISSION_SET_FAIL = new ErrorCode(1_005_001_000, "站点分成设置失败");


    // ========== 站点管理 模块 1-005-002-000 ==========
    ErrorCode ADD_PRODUCT_OPERATORID_NOT_NULL = new ErrorCode(1_005_002_000, "新增产品运营商不能为空");
    ErrorCode DEFAULT_PRODUCT_NOT_DELETE = new ErrorCode(1_005_002_001, "内置产品不能删除");
}
