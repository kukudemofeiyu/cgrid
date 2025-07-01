package com.things.cgomp.common.mq.message;

import com.things.cgomp.common.mq.common.AbstractBody;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备数据上传请求对象
 * @author things
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class DeviceDataReqMsg extends AbstractBody {

    /**
     * 交易流水号
     */
    private String orderNo;
    /**
     * 输出电压
     */
    private Double voltage;
    /**
     * 输出电流
     */
    private Double current;
    /**
     * 输出功率
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
     * 枪状态
     * 0离线 1故障 2空闲 3充电
     */
    private Integer portStatus;
    /**
     * 枪是否归位 0-否 1-是 2-未知
     */
    private Integer portHoming;
    /**
     * 是否插枪
     */
    private Boolean portInserted;
    /**
     * SOC
     */
    private Integer soc;
    /**
     * 充电度数
     */
    private Double chargeEnergy;
    /**
     * 已充金额
     */
    private Double amount;
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
     * 枪线温度
     */
    private Double gunLineTemperature;
    /**
     * 桩体温度
     */
    private Double pileBodyTemperature;
}
