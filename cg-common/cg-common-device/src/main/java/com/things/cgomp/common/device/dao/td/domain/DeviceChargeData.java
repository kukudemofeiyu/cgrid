package com.things.cgomp.common.device.dao.td.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("device_charging_data")
public class DeviceChargeData extends BasePersistData {

    /**
     * bms需求电压 V
     */
    private Double bmsDemandVoltage;
    /**
     * bms需求电流 A
     */
    private Double bmsDemandCurrent;
    /**
     * 充电模式 1-恒压充电，2-恒流充电
     */
    private Integer bmsChargeMode;
    /**
     * bms充电电压测量值
     */
    private Double bmsCheckVoltage;
    /**
     * bms充电电流测量值
     */
    private Double bmsCheckCurrent;
    /**
     * BMS最高单体动力蓄电池电压
     */
    private Double bmsMaxCellVoltage;
    /**
     * BMS 最高单体动力蓄电池电压组号
     */
    private Integer bmsMaxCellVoltageGroupNo;
    /**
     * BMS当前荷电状态SOC
     */
    private Integer bmsSoc;
    /**
     * BMS估算剩余充电时间,单位 分钟
     */
    private Integer bmsLeftChargeTime;
    /**
     * 电桩电压输出值
     */
    private Double bmsPileOutputVoltage;
    /**
     * 电桩电流输出值
     */
    private Double bmsPileOutputCurrent;
    /**
     *累计充电时间,单位 分钟
     */
    private Integer bmsTotalChargeTime;

}
