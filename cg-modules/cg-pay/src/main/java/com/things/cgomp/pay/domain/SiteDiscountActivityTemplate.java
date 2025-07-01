package com.things.cgomp.pay.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 站点活动模板表
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("pay_site_discount_activity_template")
@NoArgsConstructor
public class SiteDiscountActivityTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 模板id
     */
    private Long templateId;

    public SiteDiscountActivityTemplate(
            Long activityId,
            Long templateId
    ) {
        this.activityId = activityId;
        this.templateId = templateId;
    }
}
