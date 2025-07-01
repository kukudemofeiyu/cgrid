package com.things.cgomp.common.core.enums;

import lombok.Getter;

/**
 * 通用状态
 *
 * @author things
 */
@Getter
public enum CommonStatus {

    OK(1, "正常"), DISABLE(0, "停用"),

    DELETED(1, "删除"),NOT_DELETED(0, "未删除")

    ;

    private final Integer code;
    private final String info;

    CommonStatus(Integer code, String info)
    {
        this.code = code;
        this.info = info;
    }
}
