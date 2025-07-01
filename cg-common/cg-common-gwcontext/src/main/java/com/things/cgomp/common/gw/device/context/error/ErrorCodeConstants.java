package com.things.cgomp.common.gw.device.context.error;

import com.things.cgomp.common.core.exception.ErrorCode;

/**
 * DEVICE_GW 错误码枚举类
 *
 * 设备网关 模块，使用 1-003-000-000 段
 */
public interface ErrorCodeConstants {


    // ========== 通用  1-003-000-000 ==========
    ErrorCode INIT_BROKER_FILE = new ErrorCode(1_003_000_001, "初始化Broker配置失败");
    ErrorCode NO_CONFIG_BROKER_FILE = new ErrorCode(1_003_000_002, "没有broker配置");
    ErrorCode PUSH_SERVICE_NO_BROKER = new ErrorCode(1_003_000_003, "通信异常");
    ErrorCode NO_SUPPORT_FRAME = new ErrorCode(1_003_000_004, "不支持报文类型");
    ErrorCode SESSION_IS_NOT_EXIST = new ErrorCode(1_003_000_005, "会话异常");
    ErrorCode DEVICE_NO_ACK = new ErrorCode(1_003_000_006, "设备没有响应");
    ErrorCode SYSTEM_ERROR = new ErrorCode(1_003_000_007, "系统错误");


    // ========== 云快充BROKER   1-003-001-000 ==========
    ErrorCode RANDOM_KEY_IS_NULL = new ErrorCode(1_003_001_001, "云快充随机密钥出错");
    ErrorCode REQUEST_PAY_RULE_FAIL = new ErrorCode(1_003_001_004, "获取计费规则失败");




}
