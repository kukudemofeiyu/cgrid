package com.things.cgomp.pay.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.things.cgomp.common.core.enums.EnableEnum;
import com.things.cgomp.device.api.domain.Site;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class DiscountCouponVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 1-站点折扣 2-优惠券
     */
    private Integer discountClass;

    /**
     * 优惠券类型：0-抵扣 1-折扣 2-一口价
     */
    private Integer discountType;

    /**
     * 0-服务费 1-总费用
     */
    private Integer deductionType;

    /**
     * 可用站点维度(0-全部站点 1-部分站点)
     */
    private Integer siteDimension;

    /**
     * 优惠金额
     */
    private BigDecimal discount;

    /**
     * 是否可叠加使用：0-否 1-是
     */
    private Integer stackable;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 满足总费用额度
     */
    private BigDecimal feeLimit;

    private List<Site> sites;

    private List<Long> siteIds;

    private Site site;

    public DiscountCouponVo() {
        this.stackable = EnableEnum.DISABLE.getCode();
    }
}
