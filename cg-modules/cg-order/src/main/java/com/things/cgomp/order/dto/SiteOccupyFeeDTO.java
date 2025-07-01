package com.things.cgomp.order.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class SiteOccupyFeeDTO {

    private Long siteId;

    /**
     * 0-禁用 1-开启
     */
    private Integer previousStatus;

    /**
     * 免费时长（分钟）
     */
    private Integer previousFreeDuration;

    /**
     * 单价（元/分钟）
     */
    private BigDecimal previousUnitPrice;

    /**
     * 封顶金额（元）
     */
    private BigDecimal previousCappedAmount;

    /**
     * 0-禁用 1-开启
     */
    private Integer postStatus;

    /**
     * 免费时长（分钟）
     */
    private Integer postFreeDuration;

    /**
     * 单价（元/分钟）
     */
    private BigDecimal postUnitPrice;

    /**
     * 封顶金额（元）
     */
    private BigDecimal postCappedAmount;
}
