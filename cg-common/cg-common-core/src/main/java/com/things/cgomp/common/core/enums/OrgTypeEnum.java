package com.things.cgomp.common.core.enums;

import lombok.Getter;

/**
 * @author 组织类型枚举
 */
@Getter
public enum OrgTypeEnum {

    PLATFORM(0,
            100L,
            "平台管理员"
    ),

    OPERATOR(
            1,
            null,
            "运营商"
    )
    ;

    private final Integer type;
    /**
     * 根节点
     */
    private final Long rootId;

    private final String description;

    OrgTypeEnum(Integer type, Long rootId, String description) {
        this.type = type;
        this.rootId = rootId;
        this.description = description;
    }
}
