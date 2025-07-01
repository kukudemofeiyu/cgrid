package com.things.cgomp.pay.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActivityStatusEnum {
    NOT_STARTED(0, "未开始"),
    UNDER_WAY(1, "进行中"),
    ENDED_AUTO_EXPIRATION(2, "已结束(自动到期)"),
    ENDED_MANUAL_DISABLE(3, "已结束(手动停用)"),
    ;
    private final Integer type;

    private final String description;
}