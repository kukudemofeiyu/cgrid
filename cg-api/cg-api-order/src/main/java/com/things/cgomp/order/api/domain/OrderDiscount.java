package com.things.cgomp.order.api.domain;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单折扣明细表
 * </p>
 *
 * @author baomidou
 * @since 2025-04-01
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("order_discount")
public class OrderDiscount implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    private Long activityId;

    private Long templateId;

    private Long couponId;

    /**
     * 1-站点折扣 2-优惠券
     */
    private Integer discountClass;

    /**
     * 优惠券配置/站点活动配置
     */
    private String config;

    /**
     * 优惠券类型：0-抵扣 1-折扣 2-一口价
     */
    private Integer discountType;

    /**
     * 0-服务费 1-总费用
     */
    private Integer deductionType;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    @TableField(exist = false)
    private Object configObj;

    @TableField(exist = false)
    private String activityName;

    @TableField(exist = false)
    private String templateName;

    public OrderDiscount setConfigObj() {
        configObj = JSON.parseObject(config);
        this.config = null;
        return this;
    }

    public OrderDiscount setConfig() {
        if(configObj == null){
            return this;
        }

        this.config = JSON.toJSONString(configObj);
        return this;
    }

    private String buildActivityName(
            Map<Long, String> siteActivityNameMap,
            Map<Long, String> couponActivityNameMap
    ) {
        if (DiscountClassEnum.SITE.getType().equals(discountClass)) {
            return siteActivityNameMap.get(activityId);
        }

        return couponActivityNameMap.get(activityId);
    }

    private String buildTemplateName(
            Map<Long, String> siteTemplateNameMap,
            Map<Long, String> couponTemplateNameMap
    ) {
        if (DiscountClassEnum.SITE.getType().equals(discountClass)) {
            return siteTemplateNameMap.get(templateId);
        }

        return couponTemplateNameMap.get(templateId);
    }

    public OrderDiscount setActivityName(
            Map<Long, String> siteActivityNameMap,
            Map<Long, String> couponActivityNameMap
    ) {
        activityName = buildActivityName(
                siteActivityNameMap,
                couponActivityNameMap
        );

        return this;
    }

    public OrderDiscount setTemplateName(
            Map<Long, String> siteTemplateNameMap,
            Map<Long, String> couponTemplateNameMap
    ) {
        templateName = buildTemplateName(
                siteTemplateNameMap,
                couponTemplateNameMap
        );

        return this;
    }

}
