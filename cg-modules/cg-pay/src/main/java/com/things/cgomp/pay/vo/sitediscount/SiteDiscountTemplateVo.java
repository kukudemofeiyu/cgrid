package com.things.cgomp.pay.vo.sitediscount;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 站点折扣模板表
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
@Getter
@Setter
@Accessors(chain = true)
public class SiteDiscountTemplateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 运营商ID
     */
    private Long operatorId;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 活动类型(0-站点折扣 1-服务费折扣 2-站点一口价 3-服务费一口价)
     */
    private Integer activityType;

    /**
     * 折扣数值(折扣为%)
     */
    private BigDecimal discount;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
