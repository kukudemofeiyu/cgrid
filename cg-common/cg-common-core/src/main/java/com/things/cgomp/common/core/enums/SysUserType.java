package com.things.cgomp.common.core.enums;

import lombok.Getter;

/**
 * @author things
 */
@Getter
public enum SysUserType {

    PLATFORM_USER(0, "平台用户"),
    OPERATOR_ADMIN(1, "运营商管理员"),
    OPERATOR_USER(2, "运营商用户"),
    ;

    private final Integer type;
    private final String desc;

    SysUserType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
