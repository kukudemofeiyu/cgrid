package com.things.cgomp.order.api.enums;

import com.things.cgomp.common.core.enums.EnableEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayStatusEnum {

    NOT_PAID(0, "未支付"),
    HAVE_PAID(1, "已支付"),
    REFUNDED(2, "已退款（由退款状态聚合）"),
    ;

    private final Integer code;

    private final String desc;

    public static Integer buildPayStatus(
            Integer refundStatus,
            Integer payStatus
    ) {
        if (EnableEnum.ENABLE.getCode().equals(refundStatus)) {
            return PayStatusEnum.REFUNDED.getCode();
        }

        return payStatus;
    }
}
