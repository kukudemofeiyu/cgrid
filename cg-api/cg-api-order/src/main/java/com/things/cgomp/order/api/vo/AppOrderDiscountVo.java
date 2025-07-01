package com.things.cgomp.order.api.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class AppOrderDiscountVo {

    private BigDecimal discountAmount;

}
