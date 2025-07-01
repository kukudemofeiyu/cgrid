package com.things.cgomp.order.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderPaySuccessDTO {

    /**
     * 订单id列表
     */
    private List<Long> orderIds;

    private String payOrderId;

    /**
     * 支付时间
     */
    private Long payTime;

    private Object config;

}
