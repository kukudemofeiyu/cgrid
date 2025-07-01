package com.things.cgomp.app.enums;

/**
 * 用途枚举
 */
public enum PurposeEnum {
    /**
     * 充值
     */
    RECHARGE(0, "充值"),

    /**
     * 支付
     */
    PAY(1, "支付");

    private final int code;
    private final String description;

    PurposeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据 code 获取枚举值
     *
     * @param code 枚举 code
     * @return 对应的枚举值，如果未找到返回 null
     */
    public static PurposeEnum getByCode(int code) {
        for (PurposeEnum purpose : PurposeEnum.values()) {
            if (purpose.getCode() == code) {
                return purpose;
            }
        }
        return null;
    }
}

