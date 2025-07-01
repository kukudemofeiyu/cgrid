package com.things.cgomp.app.api.enums;

public enum OrderSource {

    /**
     * 微信支付
     */
    WECHAT(1, "微信"),

    /**
     * 支付宝支付
     */
    ALIPAY(2, "支付宝"),

    /**
     * 抖音支付
     */
    DOUYIN(3, "抖音"),
    /**
     * 系统支付
     */
    SYSTEM(4, "系统");

    private final Integer code;
    private final String description;

    OrderSource(Integer code, String description) {
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
    public static OrderSource getByCode(Integer code) {
        for (OrderSource source : OrderSource.values()) {
            if (source.getCode().equals(code)) {
                return source;
            }
        }
        return null;
    }
}

