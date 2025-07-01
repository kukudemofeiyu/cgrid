package com.things.cgomp.common.core.enums;

import lombok.Getter;

/**
 * 分成等级
 */
@Getter
public enum CommissionLevel {

    OPERATOR(1, "运营商级"),
    OPERATOR_SITE(2, "运营商-站点级"),
    SHAREHOLDERS(3, "分成者级"),            //暂不使用
    SHAREHOLDERS_SITE(4, "分成者-站点级"),   //暂不使用
    ;
    private final Integer level;
    private final String desc;

    CommissionLevel(Integer level, String desc) {
        this.level = level;
        this.desc = desc;
    }

    public static CommissionLevel getByLevel(Integer level) {
        CommissionLevel[] values = CommissionLevel.values();
        for (CommissionLevel value : values) {
            if (value.level.equals(level)) {
                return value;
            }
        }
        return null;
    }

    public boolean isOperatorLevel() {
        return CommissionLevel.OPERATOR.equals(this) || CommissionLevel.OPERATOR_SITE.equals(this);
    }

    public boolean isShareholdersLevel() {
        return CommissionLevel.SHAREHOLDERS.equals(this) || CommissionLevel.SHAREHOLDERS_SITE.equals(this);
    }

    public static boolean isOperatorLevel(Integer level) {
        CommissionLevel commissionLevel = CommissionLevel.getByLevel(level);
        return commissionLevel != null && commissionLevel.isOperatorLevel();
    }
}
