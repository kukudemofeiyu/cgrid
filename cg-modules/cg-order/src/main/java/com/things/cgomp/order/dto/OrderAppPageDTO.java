package com.things.cgomp.order.dto;

import com.things.cgomp.common.core.dto.PageDTO;
import lombok.Data;

@Data
public class OrderAppPageDTO  extends PageDTO {
    private Long userId;
    /**
     * 订单类型 0-充电订单 1-预约订单 2-占位订单
     */
    private Integer orderType;
    /**
     * 订单状态 0-全部 1-充电中 2-已完成
     */
    private Integer status;
}
