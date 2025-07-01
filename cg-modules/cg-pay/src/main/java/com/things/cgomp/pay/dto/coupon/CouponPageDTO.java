package com.things.cgomp.pay.dto.coupon;

import cn.hutool.core.date.DateUtil;
import com.things.cgomp.common.core.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CouponPageDTO extends PageDTO {

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 1-首次充电活动 2-单次充电活动 3-内部发券活动
     */
    private Integer activityType;

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

    public CouponPageDTO formatTime(){
        if(startTime != null){
            startTime = DateUtil.beginOfDay(startTime);
        }

        if(endTime != null){
            endTime = DateUtil.endOfDay(endTime);
        }

        return this;
    }

}
