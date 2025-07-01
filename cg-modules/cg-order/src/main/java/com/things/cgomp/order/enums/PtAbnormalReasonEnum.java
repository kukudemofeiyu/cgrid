package com.things.cgomp.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 平台异常订单原因
 */
@Getter
@AllArgsConstructor
public enum PtAbnormalReasonEnum {

    ORIGINAL_ORDER_NOT_FOUND(9000, "未查询到原始订单，自动生成订单"),
    ;

    private final Integer reason;

    private final String reasonDesc;
}
