package com.things.cgomp.order.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Accessors(chain = true)
public class OrderDiscountDTO {

    /**
     * 订单号列表
     */
    @NotEmpty(message = "订单号列表不能为空")
    private List<Long> orderIds;

    /**
     * 优惠券id
     */
    private List<Long> couponIds;

    /**
     * 站点活动id
     */
    private List<Long> siteActivityIds;

}
