package com.things.cgomp.common.device.pojo.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author things
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DevicePortStatusDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 枪口ID
     */
    private Long portId;
    /**
     * 状态时间
     */
    private Long eventTime;
    /**
     * 状态
     * 0离线 1故障 2空闲 3充电
     */
    private Integer status;
    /**
     * 是否插枪
     */
    private Boolean portInserted;
    /**
     * VIN码
     */
    private String vin;
    /**
     * 订单号
     */
    private String orderSn;
    /**
     * 枪是否归位 0-否 1-是 2-未知
     */
    private Integer portHoming;
}
