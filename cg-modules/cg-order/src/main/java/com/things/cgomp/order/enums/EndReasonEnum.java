package com.things.cgomp.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EndReasonEnum {

    START_CHARGING(0, "开始充电","用户开始充电"),
    DRAWN_GUN_NOT_CHARGED(1, "拨抢（未充电）","用户拔枪但未充电"),
    USER_ACTIVE_CANCEL(2, "用户主动取消","用户主动取消订单"),
    USER_ACTIVE_STOP(3, "用户主动停止","用户主动停止充电"),
    PLATFORM_SEND_STOP_COMMAND(4, "平台下发停止指令","平台下发停止充电"),
    CHARGING_PILE_FULL_ACTIVE_STOP(5, "充电桩充满主动停止 ","充电桩充满主动停止充电"),
    CHARGING_PILE_ABNORMAL_STOP(6, "充电桩异常中止 ","充电桩异常中止充电"),
    DRAWN_GUN_AFTER_CHARGING(7, "拨抢（充电结束后）","用户充电结束后拔枪"),
    MEET_SET_CONDITIONS_STOP_CHARGING(9, "满足设定条件停止充电","满足设定条件停止充电"),
    ;
    private final Integer type;

    private final String name;

    private final String description;

}