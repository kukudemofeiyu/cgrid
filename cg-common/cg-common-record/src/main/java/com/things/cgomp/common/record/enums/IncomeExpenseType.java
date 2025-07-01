package com.things.cgomp.common.record.enums;

import lombok.Getter;

/**
 * 收支类型
 */
@Getter
public enum IncomeExpenseType {
    INCOME(0, "收入"),
    EXPENSE(1, "支出");

    final Integer type;
    final String name;

    IncomeExpenseType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
}
