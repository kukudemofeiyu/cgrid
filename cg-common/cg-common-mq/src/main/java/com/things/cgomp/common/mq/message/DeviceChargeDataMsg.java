package com.things.cgomp.common.mq.message;

import com.things.cgomp.common.mq.common.AbstractBody;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class DeviceChargeDataMsg extends AbstractBody {
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
     * 实时温度
     */
    private Float realTemperature;
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
