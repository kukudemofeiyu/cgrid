package com.things.cgomp.app.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChargingRealDataVO {
    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 停车免费时长（小时）
     */
    private BigDecimal parkingFreeTime;
    /**
     * 占位费（充电前）
     */
    private BigDecimal occupyFee;

    /**
     * 桩编码
     */
    private String pileSn;
    /**
     * 端口ID
     */
    private Long portId;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 订单号
     */
    private String tradeSn;
    /**
     * 已充电量
     */
    private Double chargeEnergy;
    /**
     * 实时功率
     */
    private Double realPower;
    /**
     * 实时电压
     */
    private Double realVoltage;
    /**
     * 实时电流
     */
    private Double realCurrent;
    /**
     * 温度
     */
    private Double temperature;
    /**
     * 总费用
     */
    private Double totalCost;
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
