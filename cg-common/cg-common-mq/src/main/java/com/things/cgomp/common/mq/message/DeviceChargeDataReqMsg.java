package com.things.cgomp.common.mq.message;

import com.things.cgomp.common.mq.common.AbstractBody;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author things
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class DeviceChargeDataReqMsg extends AbstractBody {


    private String orderSn;
    /**--------------------------充电过程BMS需求充电机输出---------------------------------------**/
    /**
     * bms需求电压 V
     */
    private Double bmsOVoltageXQ;

    /**
     * bms需求电流 A
     */
    private Double bmsOCurrentXQ;

    /**
     * 充电模式 1-恒压充电，2-恒流充电
     */
    private Integer bmsOChargeMode;

    /**
     * bms充电电压测量值
     */
    private Double bmsOVoltageMeasure;

    /**
     * bms充电电流测量值
     */
    private Double bmsOCurrentMeasure;

    private Double  bmsOMaxVoltage;

    private Integer bmsOMaxVoltageGroupNo;

    private Integer bmsOSoc;

    /**
     * BMS估算剩余充电时间,单位 分钟
     */
    private Integer  bmsOChargeTime;

    /**
     * 电桩电压输出值
     */
    private Double bmsOVoltageOutput;

    /**
     *电桩电流输出值
     */
    private Double bmsOCurrentOutput;


    /**
     *累计充电时间,单位 分钟
     */
    private Integer  bmsOTotalChargeTime;
    /**---------------------------我是分割线--------------------------------------**/



}
