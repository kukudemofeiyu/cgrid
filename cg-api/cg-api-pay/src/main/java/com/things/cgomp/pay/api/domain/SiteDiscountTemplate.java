package com.things.cgomp.pay.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.things.cgomp.pay.api.enums.EffectiveTypeEnum;
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
@TableName("pay_site_discount_template")
public class SiteDiscountTemplate implements Serializable {

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
     * 生效类型(0-全天 1-时段)
     */
    private Integer effectiveType;

    /**
     * 时段开始时间
     */
    private String startTime;

    /**
     * 时段结束时间
     */
    private String endTime;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private Long updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 0-正常 1-删除
     */
    private Integer delFlag;

    public SiteDiscountTemplate formatTime() {
        if(EffectiveTypeEnum.ALL_DAY.getType().equals(effectiveType)){
            startTime = "00:00";
            endTime = "24:00";
        }

        return this;
    }
}
