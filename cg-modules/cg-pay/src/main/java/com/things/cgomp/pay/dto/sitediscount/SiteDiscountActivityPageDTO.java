package com.things.cgomp.pay.dto.sitediscount;

import com.things.cgomp.common.core.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SiteDiscountActivityPageDTO extends PageDTO {

    /**
     * 活动名称
     */
    private String name;

    /**
     * 运营商名称
     */
    private String operatorName;

    /**
     * 0-未开始 1-进行中 2-已结束(自动到期) 3-已结束(手动停用)
     */
    private Integer status;

    /**
     * 活动开始时间
     */
    private String startTime;

    /**
     * 活动结束时间
     */
    private String endTime;
}
