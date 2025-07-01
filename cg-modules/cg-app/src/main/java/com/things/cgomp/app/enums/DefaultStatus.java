package com.things.cgomp.app.enums;

public enum DefaultStatus {
    NON_DEFAULT(0, "非默认"),
    DEFAULT(1, "默认");

    private final int code;
    private final String description;

    DefaultStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    // 根据 code 获取 DefaultStatus
    public static DefaultStatus fromCode(int code) {
        for (DefaultStatus status : DefaultStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid default status code: " + code);
    }
}

