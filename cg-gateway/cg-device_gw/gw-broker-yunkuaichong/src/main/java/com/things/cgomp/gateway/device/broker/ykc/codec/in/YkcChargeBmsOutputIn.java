package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class YkcChargeBmsOutputIn extends YkcMessageIn{

    private String orderNo;

    private String deviceNo;

    private String gunNo;

    /**
     * bms需求电压 V
     */
    private Double bmsVoltageXQ;

    /**
     * bms需求电流 A
     */
    private Double bmsCurrentXQ;

    /**
     * 充电模式 1-恒压充电，2-恒流充电
     */
    private Integer chargeMode;

    /**
     * bms充电电压测量值
     */
    private Double bmsVoltageMeasure;

    /**
     * bms充电电流测量值
     */
    private Double bmsCurrentMeasure;

    private Double  maxVoltage;

    private Integer maxVoltageGroupNo;

    private Integer soc;

    /**
     * BMS估算剩余充电时间,单位 分钟
     */
    private Integer  bmsChargeTime;

    /**
     * 电桩电压输出值
     */
    private Double voltageOutput;

    /**
     *电桩电流输出值
     */
    private Double currentOutput;


    /**
     *累计充电时间,单位 分钟
     */
    private Integer  totalChargeTime;
}
