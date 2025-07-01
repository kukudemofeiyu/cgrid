package com.things.cgomp.pay.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 站点活动站点表
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("pay_site_discount_activity_site")
@NoArgsConstructor
public class SiteDiscountActivitySite implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 站点id
     */
    private Long siteId;

    public SiteDiscountActivitySite(
            Long activityId,
            Long siteId
    ) {
        this.activityId = activityId;
        this.siteId = siteId;
    }
}
