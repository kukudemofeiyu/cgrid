package com.things.cgomp.device.dto.device;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class ChargePortDTO {

    private Long deviceId;

    /**
     * 充电类型，0-快充，1-慢充
     */
    private Integer chargeType;

    /**
     * 枪口编号
     */
    @NotNull
    private String aliasSn;

    @NotNull
    private String name;

    /**
     * 桩id
     */
    private Long pileId;

    /**
     * 状态，0-禁用，1-启用
     */
    private Integer status;

}
