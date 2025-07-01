package com.things.cgomp.pay.dto.coupon;

import com.things.cgomp.common.core.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CouponTemplatePageDTO extends PageDTO {

    /**
     * id
     */
    private Long id;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 优惠券类型：0-现金券 1-折扣券
     */
    private Integer couponType;

    /**
     * 可抵扣费用(0-服务费 1-总费用)
     */
    private Integer deductionType;

}
