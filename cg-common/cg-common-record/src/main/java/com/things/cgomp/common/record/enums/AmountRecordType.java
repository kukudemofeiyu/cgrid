package com.things.cgomp.common.record.enums;

import lombok.Getter;

/**
 * 金额记录类型
 * @author things
 * @date 2025/3/6
 */
@Getter
public enum AmountRecordType {

    USER_RECHARGE(1, "用户充值"),
    ORDER_PAYMENT(2, "订单支付"),
    WITHDRAW(3, "提现"),
    SYSTEM_RECHARGE(4, "系统充值"),
    REFUND(5, "退款"),
    COMMISSION(6, "分成")

    ;


    final Integer type;
    final String name;

    AmountRecordType(Integer type, String name){
        this.type = type;
        this.name = name;
    }

    /**
     * 判断类型是否可退款
     * @param type 类型
     * @return true/false
     */
    public static boolean isRefundType(Integer type){
        return USER_RECHARGE.getType().equals(type) || SYSTEM_RECHARGE.getType().equals(type);
    }
}
