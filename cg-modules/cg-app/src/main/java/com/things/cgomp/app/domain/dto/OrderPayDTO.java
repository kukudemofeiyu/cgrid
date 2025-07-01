package com.things.cgomp.app.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class OrderPayDTO {
    /**
     * 订单id集合
     */
    @NotEmpty(message = "订单Id集合不能为空")
    private List<Long> ids;

    /**
     * 优惠券id
     */
    private List<Long> couponIds;

    /**
     * 站点活动id
     */
    private List<Long> siteActivityIds;

}
