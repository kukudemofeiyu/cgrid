package com.things.cgomp.common.device.enums;

/**
 * 充电枪状态变化标识
 * 用于标识状态具体变化
 * @author things
 */
public enum PortStatusChangeFlag {

    inserted,           // 变化为【插枪】
    drawn,              // 变化为【拔枪】
    homing,             // 变化为【归位】
    free,               // 变化为【空闲】
    charging,           // 变化为【充电中】
    order_change        // 订单号修改
}
