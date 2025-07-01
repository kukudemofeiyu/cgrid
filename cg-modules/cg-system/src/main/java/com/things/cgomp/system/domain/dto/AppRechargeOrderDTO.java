package com.things.cgomp.system.domain.dto;

import com.things.cgomp.common.core.dto.PageDTO;
import lombok.Data;

import java.util.Date;

@Data
public class AppRechargeOrderDTO extends PageDTO {
    /**
     * 订单号
     */
    private String sn;
    /**
     * 支付方式(1-微信 4-系统)
     */
    private Integer type;
    /**
     * 订单状态(1-待支付 2-已支付 3-已退款 4-已取消)
     */
    private Integer status;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;
}
