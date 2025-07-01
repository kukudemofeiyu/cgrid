package com.things.cgomp.order.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AppOrderDiscountDTO {

    private Long orderId;

    /**
     * 优惠券id列表
     */
    private List<Long> couponIds;

    /**
     * 站点活动id列表
     */
    private List<Long> siteActivityIds;

}
