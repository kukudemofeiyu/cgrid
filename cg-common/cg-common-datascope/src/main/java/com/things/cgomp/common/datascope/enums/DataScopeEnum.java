package com.things.cgomp.common.datascope.enums;

import lombok.Getter;

/**
 * @author things
 */
@Getter
public enum DataScopeEnum {

    ALL("1", "全部数据权限"),
    CUSTOM("2", "自定义数据权限"),
    ORG("3", "组织数据权限"),
    ORG_AND_CHILD("4", "组织及以下数据权限"),
    SELF("5", "仅本人数据权限"),
    CUSTOM_PLATFORM_ROLE("6", "平台角色自定义数据权限（组织维度）"),
    CUSTOM_OPERATOR_ROLE("7", "运营商自定义数据权限（组织、站点维度）"),
    ;

    private final String code;

    private final String description;

    DataScopeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static DataScopeEnum getDataScopeEnum(String code) {
        for (DataScopeEnum dataScopeEnum : DataScopeEnum.values()) {
            if (dataScopeEnum.getCode().equals(code)) {
                return dataScopeEnum;
            }
        }
        return null;
    }
}
