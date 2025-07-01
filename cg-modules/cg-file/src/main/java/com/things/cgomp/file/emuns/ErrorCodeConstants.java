package com.things.cgomp.file.emuns;


import com.things.cgomp.common.core.exception.ErrorCode;

/**
 * 文件 错误码枚举类
 * file 模块，使用1-010-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== 文件客户端相关 1-010-000-000 =========
    ErrorCode NOT_SUPPORT_OPERATION = new ErrorCode(1_010_000_000, "不支持的操作");

    // ========== 接口相关 1-010-001-000 =========
    ErrorCode FILE_CLIENT_IS_NULL = new ErrorCode(1_010_001_000, "文件存储器为空");
    ErrorCode FILE_UPLOAD_FAIL = new ErrorCode(1_010_001_001, "文件上传失败");
    ErrorCode FILE_URL_PRE_SIGN_FAIL = new ErrorCode(1_010_001_002, "生成文件上传签名URL失败");
}
