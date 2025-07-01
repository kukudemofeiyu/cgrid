package com.things.cgomp.order.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author things
 */
@Data
@Accessors(chain = true)
public class OrderProcessReqDTO implements Serializable {

    @NotNull(message = "订单ID不能为空")
    private Long orderId;
}
