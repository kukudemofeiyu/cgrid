package com.things.cgomp.common.device.dao.device.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 充电枪变化标识对象
 * 用于记录变化前的数据
 * @author things
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PortChangeValue {

    /**
     * 插枪时间
     */
    private Long insertTime;
    /**
     * 变化前订单号
     */
    private String orderSn;
    /**
     * 变化前VIN号
     */
    private String vin;
}
