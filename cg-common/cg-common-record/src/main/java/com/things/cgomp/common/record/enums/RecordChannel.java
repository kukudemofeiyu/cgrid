package com.things.cgomp.common.record.enums;

import lombok.Getter;

/**
 * 记录渠道枚举
 * @author things
 * @date 2025/3/6
 */
@Getter
public enum RecordChannel {

    ACCOUNT_BALANCE(1, "账户余额"),
    IC_CARD(2, "IC卡"),
    WECHAT_CREDIT(3, "微信授信");

    final Integer channel;
    final String name;

    RecordChannel(Integer channel, String name){
        this.channel = channel;
        this.name = name;
    }
}
