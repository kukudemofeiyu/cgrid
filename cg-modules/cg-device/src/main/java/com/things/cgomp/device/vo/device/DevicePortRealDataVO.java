package com.things.cgomp.device.vo.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 充电枪实时数据
 * @author things
 */
@Data
@Accessors(chain = true)
public class DevicePortRealDataVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 归位状态
     * 0-否 1-是 2-未知
     */
    private Integer homeStatus;
    /**
     * 充电枪状态
     * 0-离线 1-故障 2-空闲 3-充电 4-已插枪 5-占用
     */
    private Integer portStatus;
    /**
     * 状态开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date statusStartTime;
    /**
     * 状态持续时间
     */
    private String statusDuration;
    /**
     * SOC
     */
    private Integer soc;
    /**
     * 充电量
     */
    private  Double chargeEnergy;
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
     * 车辆VIN码
     */
    private String vin;
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 车牌号
     */
    private String platNumber;
}
