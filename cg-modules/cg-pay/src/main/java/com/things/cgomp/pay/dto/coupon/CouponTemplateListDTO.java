package com.things.cgomp.pay.dto.coupon;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.things.cgomp.common.core.enums.EnableEnum;
import com.things.cgomp.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CouponTemplateListDTO extends BaseEntity {

    /**
     * 0-暂停使用 1-正常
     */
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    public CouponTemplateListDTO() {
        this.status = EnableEnum.ENABLE.getCode();
    }

    public CouponTemplateListDTO formatTime(){
        if(startTime != null){
            startTime = DateUtil.beginOfDay(startTime);
        }

        return this;
    }
}
