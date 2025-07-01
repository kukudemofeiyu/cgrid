package com.things.cgomp.common.device.dao.td.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 设备充电枪数据表 device_port_data
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("device_port_data")
public class DevicePortData extends BasePersistData {

    private static final long serialVersionUID = 1L;

    /**
     * 电压
     */
    private Double voltage;
    /**
     * 电流
     */
    private Double current;
    /**
     * 功率
     */
    private Double power;
    /**
     * 温度
     */
    private Double temperature;
    /**
     * 枪号
     */
    private String portSn;
    /**
     * SOC
     */
    private Integer soc;
    /**
     * 充电度数
     */
    private Double chargeEnergy;
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
    /**
     * 已充金额
     */
    private Double amount;
    /**
     * 枪状态
     * 0离线 1故障 2空闲 3充电
     */
    private Integer portStatus;
    /**
     * 枪是否归位
     */
    private Integer portHoming;
    /**
     * 是否插枪
     */
    private Boolean portInserted;
    /**
     * 枪线温度
     */
    private Double gunLineTemperature;
    /**
     * 电池组最高温度
     */
    private Double batteryGroupMaxTemp;
    /**
     * 桩体温度
     */
    private Double pileBodyTemperature;
}
