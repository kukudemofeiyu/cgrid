package com.things.cgomp.pay.dto.coupon;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ActivityCouponDTO {

    /**
     * 模板id
     */
    private Long templateId;

    /**
     * 每人张数
     */
    private Integer countPerPerson;

}
