package com.things.cgomp.order.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 站点占位费表
 * </p>
 *
 * @author baomidou
 * @since 2025-03-02
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("order_site_occupy_fee")
public class SiteOccupyFee implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private Long siteId;

    /**
     * 0-前置 1-后置
     */
    private Integer type;

    /**
     * 0-禁用 1-开启
     */
    private Integer status;

    /**
     * 免费时长（分钟）
     */
    private Integer freeDuration;

    /**
     * 单价（元/分钟）
     */
    private BigDecimal unitPrice;

    /**
     * 封顶金额（元）
     */
    private BigDecimal cappedAmount;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
