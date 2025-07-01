package com.things.cgomp.common.core.enums;

import lombok.Getter;

/**
 * @author 内置管理员角色枚举
 */
@Getter
public enum BuiltInAdminRoleEnum {

    ADMIN_PLATFORM(
            2L,
            0,
            "平台管理员"
    ),

    ADMIN_OPERATOR(
            3L,
            0,
            "运营商管理员"
    )
    ;

    private final Long roleId;
    /**
     * 组织类型：0平台管理员 1运营商管理员
     */
    private final Integer orgType;
    private final String description;

    BuiltInAdminRoleEnum(Long roleId, Integer orgType, String description) {
        this.roleId = roleId;
        this.orgType = orgType;
        this.description = description;
    }
}
