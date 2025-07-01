package com.things.cgomp.common.record.enums;

import lombok.Getter;

/**
 * 记录状态枚举
 * @author things
 * @date 2025/3/6
 */
@Getter
public enum RecordStatus {

    FAIL(0, "失败"),
    SUCCESS(1, "成功/正常"),
    ING(2, "处理中/审核中")
    ;

    final Integer status;
    final String name;

    RecordStatus(Integer status, String name){
        this.status = status;
        this.name = name;
    }
}
