package com.things.cgomp.device.api.model.vo;

import lombok.Data;

@Data
public class ChargingPort {

    /**
     * 枪名称
     */
    private String name;

    /**
     * 当component=1,枪口状态，0-离线 1-故障 2-空闲 3-充电 4-已插枪 5-占用
     */
    private Integer portStatus;
    /**
     * 最大功率
     */
    private String maxPower;

}
