package com.things.cgomp.order.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@Accessors(chain = true)
public class SiteDiscountVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 站点活动id
     */
    private Long activityId;

    /**
     * 模板idid
     */
    private Long templateId;

    /**
     * 活动类型(0-站点折扣 1-服务费折扣 2-站点一口价 3-服务费一口价)
     */
    private Integer activityType;

    /**
     * 折扣数值(折扣为%)
     */
    private BigDecimal discount;

}
