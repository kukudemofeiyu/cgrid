package com.things.cgomp.app.enums;

public enum OrderStatus {

    /**
     * 待支付
     */
    PENDING(0, "待支付"),

    /**
     * 支付成功
     */
    SUCCESS(1, "支付成功"),

    /**
     * 支付失败
     */
    FAILED(2, "支付失败"),

    /**
     * 已退款
     */
    REFUNDED(3, "已退款");

    private final Integer code;
    private final String description;

    OrderStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据code获取枚举值
     */
    public static OrderStatus getByCode(Integer code) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}

