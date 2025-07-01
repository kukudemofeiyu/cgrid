package com.things.cgomp.app.domain.dto;

import lombok.Data;

@Data
public class ChargingRealDataDTO {
    /**
     * 端口ID
     */
    private Long portId;
    /**
     * 已充电量
     */
    private Double chargeEnergy;
    /**
     * 实时功率
     */
    private Float realPower;
    /**
     * 实时电压
     */
    private Float realVoltage;
    /**
     * 实时电流
     */
    private Float realCurrent;
    /**
     * 总费用
     */
    private Float totalCost;
    /**
     * SOC
     */
    private Integer soc;
    /**
     * 已充电时间
     * 分钟
     */
    private Integer timeCharge;
    /**
     * 剩余时间
     * 分钟
     */
    private Integer timeLeft;
}
