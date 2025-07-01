package com.things.cgomp.pay.api.dto;

import cn.hutool.core.date.DateUtil;
import com.things.cgomp.common.core.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CouponAppPageDTO extends PageDTO {

    /**
     * 手机号码
     */
    private Long userId;
    /**
     * 0-未使用 1-已使用 2-失效
     */
    private Integer status;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;


    }

