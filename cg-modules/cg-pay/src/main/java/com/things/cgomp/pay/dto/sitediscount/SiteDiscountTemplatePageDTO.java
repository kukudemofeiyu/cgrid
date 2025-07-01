package com.things.cgomp.pay.dto.sitediscount;

import com.things.cgomp.common.core.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SiteDiscountTemplatePageDTO extends PageDTO {

    /**
     * 模板名称
     */
    private String name;

    /**
     * 活动类型(0-站点折扣 1-服务费折扣 2-站点一口价 3-服务费一口价)
     */
    private Integer activityType;

}
