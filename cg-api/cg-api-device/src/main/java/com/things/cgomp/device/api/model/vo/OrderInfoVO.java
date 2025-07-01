package com.things.cgomp.device.api.model.vo;

import lombok.Data;

@Data
public class OrderInfoVO {
    /**
     * 订单id
     */
    private Long id;
    /**
     * 交易流水号
     */
    private String tradeSn;

}
