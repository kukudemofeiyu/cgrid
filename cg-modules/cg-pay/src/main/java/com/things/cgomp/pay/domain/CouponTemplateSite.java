package com.things.cgomp.pay.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 优惠券模板站点表
 * </p>
 *
 * @author baomidou
 * @since 2025-03-25
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("pay_coupon_template_site")
@NoArgsConstructor
public class CouponTemplateSite implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 模板id
     */
    private Long templateId;

    /**
     * 站点id
     */
    private Long siteId;

    public CouponTemplateSite(
            Long templateId,
            Long siteId
    ) {
        this.templateId = templateId;
        this.siteId = siteId;
    }
}
