package com.things.cgomp.device.dto.device;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class ChargeGridDTO {


    /**
     * 设备唯一序列
     */
    @NotNull(message = "充电桩编号不能为空！")
    private String sn;

    /**
     * 设备名称
     */
    @NotNull
    private String name;

    /**
     * 充电类型，0-快充，1-慢充
     */
    @NotNull
    private Integer chargeType;

    /**
     * 电流信息（A）
     */
    @NotNull
    private String electric;

    /**
     * 电压信息（V）
     */
    @NotNull
    private String voltage;

    /**
     * 最大功率（kW）
     */
    @NotNull
    private String maxPower;

    /**
     * 状态，0-禁用，1-启用
     */
    private Integer status;

    /**
     * 枪口数
     */
    @NotNull
    private Integer portNum;

    /**
     * 站点id
     */
    @NotNull
    private Long siteId;

    /**
     * 是否收费，0-否，1-是
     */
    private Integer isFree;

    /**
     * 产品型号id
     */
    private Integer productId;

    /**
     * 运营商ID
     */
    @NotNull
    private Long operatorId;


    private String sim;

    /**
     * SIM卡过期时间
     */
    private String simExpire;





}
