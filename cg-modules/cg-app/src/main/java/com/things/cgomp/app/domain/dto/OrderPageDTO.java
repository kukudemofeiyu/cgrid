package com.things.cgomp.app.domain.dto;

import com.things.cgomp.common.core.dto.PageDTO;
import lombok.Data;

@Data
public class OrderPageDTO extends PageDTO {
    /**
     * 订单类型 0-充电订单 1-预约订单 2-占位订单
     */
    private Integer orderType;
    /**
     * 订单状态 0-全部 1-充电中 2-已完成 3-待支付
     */
    private Integer status;
}
