package com.things.cgomp.common.record.enums;

import lombok.Getter;

/**
 * 明细记录模块枚举
 *
 * @author things
 * @date 2025/3/6
 */
@Getter
public enum RecordModule {

    OPERATOR(1, "运营商模块"),
    SHAREHOLDERS(2, "分成者模块"),
    APP_USER(3, "注册用户模块"),
    IC_CARD(4, "IC卡模块");

    final Integer module;
    final String name;

    RecordModule(Integer module, String name) {
        this.module = module;
        this.name = name;
    }

    public static RecordModule get(Integer module) {
        RecordModule[] values = RecordModule.values();
        for (RecordModule value : values) {
            if (value.getModule().equals(module)) {
                return value;
            }
        }
        return null;
    }
}
