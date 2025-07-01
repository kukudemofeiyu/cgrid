package com.things.cgomp.app.enums;

public enum BindStatus {
    BOUND(0, "已绑定"),
    UNBOUND(1, "已解绑");

    private final int code;
    private final String description;

    BindStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    // 根据 code 获取 BindStatus
    public static BindStatus fromCode(int code) {
        for (BindStatus status : BindStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid bind status code: " + code);
    }
}
