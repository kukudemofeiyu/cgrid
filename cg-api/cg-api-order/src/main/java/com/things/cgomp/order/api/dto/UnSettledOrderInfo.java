package com.things.cgomp.order.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author things
 */
@Data
public class UnSettledOrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private Long id;
    /**
     * 订单号
     */
    private String sn;
    /**
     * 充电桩ID
     */
    private Long pileId;
    /**
     * 充电枪ID
     */
    private Long portId;
}
