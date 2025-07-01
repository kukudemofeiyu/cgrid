package com.things.cgomp.system.enums;


import com.things.cgomp.common.core.exception.ErrorCode;

/**
 * SYSTEM 错误码枚举类
 * 系统 模块，使用 1-004-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== 系统 模块 1-004-000-000 ==========
    ErrorCode SYSTEM_INNER_CONFIG_CAN_NOT_DELETE = new ErrorCode(1_004_000_000, "内置参数【{}】不能删除");

    // ========== USER 模块 1-004-001-000 ==========
    ErrorCode USER_SUPER_ADMIN_CANT_NOT_OPERATOR = new ErrorCode(1_004_001_000, "不允许操作超级管理员用户");
    ErrorCode USER_NO_PERMISSION = new ErrorCode(1_004_001_001, "没有权限访问用户数据！");
    ErrorCode USER_IMPORT_NOT_EMPTY = new ErrorCode(1_004_001_002, "导入用户数据不能为空！");
    ErrorCode USER_USERNAME_IS_EXIST = new ErrorCode(1_004_001_003, "登录用户名【{}】已存在");
    ErrorCode USER_MOBILE_IS_EXIST = new ErrorCode(1_004_001_004, "手机号码【{}】已存在");
    ErrorCode USER_CURRENT_NOT_DELETE = new ErrorCode(1_004_001_005, "当前用户不能删除");
    ErrorCode USER_USER_ID_NOT_NULL = new ErrorCode(1_004_001_006, "用户ID不能为空");
    ErrorCode USER_USERNAME_NOT_NULL = new ErrorCode(1_004_001_007, "用户名不能为空");
    ErrorCode USER_PASSWORD_NOT_NULL = new ErrorCode(1_004_001_008, "用户{}密码不能为空");
    ErrorCode USER_PASSWORD_NOT_MATCH = new ErrorCode(1_004_001_009, "用户密码不正确");
    ErrorCode USER_EMAIL_IS_EXIST = new ErrorCode(1_004_001_010, "邮箱【{}】已存在");
    ErrorCode USER_OLD_PASSWORD_NOT_RIGHT = new ErrorCode(1_004_001_011, "旧密码错误");
    ErrorCode USER_NEW_PASSWORD_CANNOT_BE_SAME_OLD_PASSWORD = new ErrorCode(1_004_001_012, "新密码不能与旧密码相同");
    ErrorCode USER_OPERATOR_CANT_NOT_OPERATOR = new ErrorCode(1_004_001_013, "不允许操作运营商管理员用户");

    // ========== ROLE 模块 1-004-002-000 ==========
    ErrorCode ROLE_NAME_IS_EXIST = new ErrorCode(1_004_002_000, "角色名称已存在");
    ErrorCode ROLE_IS_ASSIGN = new ErrorCode(1_004_002_001, "角色【{}】已分配,不能删除");
    ErrorCode ROLE_MENU_IDS_NOT_NULL = new ErrorCode(1_004_002_002, "菜单不能为空");
    ErrorCode ROLE_SUPER_ADMIN_CANT_NOT_OPERATOR = new ErrorCode(1_004_002_003, "不允许操作超级管理员角色");
    ErrorCode ROLE_BUILTIN_CANT_NOT_OPERATOR = new ErrorCode(1_004_002_004, "不允许操作预定义角色");

    // ========== MENU 模块 1-004-003-000 ==========
    ErrorCode MENU_NAME_IS_EXIST = new ErrorCode(1_004_003_000, "菜单名称已存在");
    ErrorCode MENU_FRAME_NEED_HTTPS = new ErrorCode(1_004_003_001, "外链地址必须以http(s)://开头");
    ErrorCode MENU_PARENT_CAN_NOT_SELF = new ErrorCode(1_004_003_002, "上级菜单不能选择自己");
    ErrorCode MENU_HAS_CHILD_CAN_NOT_DELETE = new ErrorCode(1_004_003_003, "存在子菜单,不允许删除");
    ErrorCode MENU_HAS_ASSIGN_CAN_NOT_DELETE = new ErrorCode(1_004_003_004, "菜单已分配,不允许删除");
    ErrorCode MENU_ID_IS_NOT_NULL = new ErrorCode(1_004_003_005, "菜单ID不能为空");

    // ========== 运营商 模块 1-004-004-000 ==========
    ErrorCode OPERATOR_ID_IS_NOT_NULL = new ErrorCode(1_004_004_001, "运营商ID不能为空");
    ErrorCode OPERATOR_HAS_CHILD_CAN_NOT_DELETE = new ErrorCode(1_004_004_002, "运营商包含子分成者,不允许删除");
    ErrorCode OPERATOR_ORG_HAS_CHILD_CAN_NOT_DELETE = new ErrorCode(1_004_004_003, "运营商包含子运营商,不允许删除");
    ErrorCode OPERATOR_COMMISSION_SET_FAIL = new ErrorCode(1_004_004_004, "运营商分成设置失败");
    ErrorCode OPERATOR_DEFAULT_FEE_RULE_EMPTY = new ErrorCode(1_004_004_005, "无默认计费规则");
    ErrorCode OPERATOR_PASSWORD_NOT_NULL = new ErrorCode(1_004_004_006, "运营商密码不能为空");
    ErrorCode OPERATOR_ORG_IS_NULL = new ErrorCode(1_004_004_007, "请选择运营商节点");
    ErrorCode OPERATOR_INSERT_FAIL = new ErrorCode(1_004_004_008, "新增运营商失败");
    ErrorCode OPERATOR_ROLE_IS_NULL = new ErrorCode(1_004_004_009, "运营商角色不能为空");

    // ========== 分成者 模块 1-004-005-000 ==========
    ErrorCode COMMISSION_ID_IS_NOT_NULL = new ErrorCode(1_004_004_001, "分成者ID不能为空");
    ErrorCode COMMISSION_RULE_ID_IS_NOT_NULL = new ErrorCode(1_004_005_002, "分成规则ID不能为空");
    ErrorCode SHAREHOLDERS_COMMISSION_SET_FAIL = new ErrorCode(1_004_005_003, "分成者分成设置失败");
    ErrorCode SHAREHOLDERS_COMMISSION_ADD_FAIL = new ErrorCode(1_004_005_004, "分成者分成添加失败");

    // ========== 用户账号 模块 1-004-006-000 ==========
    ErrorCode USER_ACCOUNT_IS_EXIST = new ErrorCode(1_004_006_000, "该用户账号已存在");
    ErrorCode USER_ACCOUNT_IS_NULL = new ErrorCode(1_004_006_001, "用户账号不存在");
    ErrorCode USER_ACCOUNT_BALANCE_UPDATE_FAIL = new ErrorCode(1_004_006_002, "账户余额更新失败");
    ErrorCode USER_ACCOUNT_BALANCE_NOT_ENOUGH = new ErrorCode(1_004_006_003, "账户余额不足");

    // ========== 明细记录 模块 1-004-007-000 ==========
    ErrorCode RECORD_AMOUNT_INSERT_ERROR  = new ErrorCode(1_004_007_000, "新增金额记录失败");
    ErrorCode RECORD_AMOUNT_IS_NULL = new ErrorCode(1_004_007_001, "金额记录不存在");
    ErrorCode RECORD_AMOUNT_MODULE_IS_NULL = new ErrorCode(1_004_007_002, "金额记录所属模块不能为空");

    ErrorCode RECORD_WITHDRAW_INSERT_ERROR  = new ErrorCode(1_004_007_100, "新增提现记录失败");

    // ========== 注册用户 模块 1-004-008-000 ==========
    ErrorCode APP_USER_MOBILE_NOT_EXIST = new ErrorCode(1_004_008_000, "用户手机号不存在");
    ErrorCode APP_USER_BLACKLIST_ID_IS_NULL = new ErrorCode(1_004_008_001, "注册用户黑名单ID不能为空");
    ErrorCode APP_USER_BLACKLIST_EXIST = new ErrorCode(1_004_008_002, "[{}]存在未解禁的黑名单记录");
    ErrorCode APP_USER_SYSTEM_RECHARGE_FAIL = new ErrorCode(1_004_008_003, "系统充值失败");
    ErrorCode APP_USER_REFUND_FAIL = new ErrorCode(1_004_008_004, "退款失败");
    ErrorCode APP_USER_REFUND_TYPE_NOT_SUPPORT = new ErrorCode(1_004_008_005, "不支持退款操作");
    ErrorCode APP_USER_REFUND_AMOUNT_NOT_ALLOW = new ErrorCode(1_004_008_006, "退款金额不能多于[{}]元");
    ErrorCode APP_RECHARGE_FAIL = new ErrorCode(1_004_008_007, "APP充值失败");

    // ========== 个人中心相关 模块 1-004-009-000 ==========
    ErrorCode USER_AVATAR_NOT_NULL = new ErrorCode(1_004_009_000, "头像不能为空");
    ErrorCode USER_AVATAR_FILE_FORMAT_BE_INCORRECT = new ErrorCode(1_004_009_001, "文件格式不正确");
    ErrorCode USER_AVATAR_UPLOAD_FAIL = new ErrorCode(1_004_009_002, "头像上传失败");

    // ========== 组织管理 模块 1-004-010-000 ==========
    ErrorCode ORG_IS_DISABLE = new ErrorCode(1_004_010_000, "组织已停用");
}
