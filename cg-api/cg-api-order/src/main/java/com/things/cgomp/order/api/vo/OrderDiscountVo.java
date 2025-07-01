package com.things.cgomp.order.api.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class OrderDiscountVo {

    private BigDecimal payAmount;

    private Object config;

}
