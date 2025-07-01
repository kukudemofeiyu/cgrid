package com.things.cgomp.device.dto.device;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateChargeGridDTO {

    private Long deviceId;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 充电类型，0-快充，1-慢充
     */
    private Integer chargeType;

    /**
     * 电流信息（A）
     */
    private String electric;

    /**
     * 电压信息（V）
     */
    private String voltage;

    /**
     * 最大功率（kW）
     */
    private String maxPower;

    /**
     * 状态，0-禁用，1-启用
     */
    private Integer status;

    /**
     * 是否收费，0-否，1-是
     */
    private Integer isFree;

    /**
     * 产品型号id
     */
    private Integer productId;

}
