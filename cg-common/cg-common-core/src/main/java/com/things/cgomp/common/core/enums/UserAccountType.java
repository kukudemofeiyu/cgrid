package com.things.cgomp.common.core.enums;

import lombok.Getter;

/**
 * @author things
 * @date 2025/2/27
 */
@Getter
public enum UserAccountType {

    WEB("01", "平台用户"),
    APP("02", "小程序用户")
    ;

    private final String code;
    private final String info;

    UserAccountType(String code, String info) {
        this.code = code;
        this.info = info;
    }
}
