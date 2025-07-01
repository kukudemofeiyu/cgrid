package com.things.cgomp.devicescale.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 充电桩设备表
 * </p>
 *
 * @author baomidou
 * @since 2025-02-28
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("device_info")
public class DeviceInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 设备编号
     */
    @TableId(type = IdType.AUTO)
    private Long deviceId;

    /**
     * 设备唯一序列
     */
    private String sn;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 充电类型，0-快充，1-慢充
     */
    private Integer chargeType;

    /**
     * 电流信息（A）
     */
    private String electric;

    /**
     * 电压信息（V）
     */
    private String voltage;

    /**
     * 最大功率（kW）
     */
    private String maxPower;

    /**
     * 状态，0-禁用，1-启用
     */
    private Integer status;

    /**
     * 站点id
     */
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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 运营商id
     */
    private Long operatorId;

    /**
     * 设备备注信息
     */
    private String remark;

    /**
     * 在同一父设备中具有唯一性,可用于端口ID，用来和硬件交互
     */
    private String aliasSn;

    /**
     * 上级设备ID
     */
    private Long parentId;

    /**
     * 通信设备ID
     */
    private Long connectDeviceId;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 修改人
     */
    private Integer updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 祖级设备id列表
     */
    private String ancestorIds;

    /**
     * 当component=1,枪口状态，0-离线 1-故障 2-空闲 3-充电 4-已插枪 5-占用
     */
    @TableField(exist = false)
    private Integer portStatus;

    /**
     * 端口数
     */
    private Integer portNum;

    private String sim;

    /**
     * SIM卡过期时间
     */
    private String simExpire;

    /**
     * 0-桩（point） 1-枪（port）
     */
    private Integer component;

    /**
     * 计费规则id
     */
    private Long payRuleId;

    /**
     * 激活时间（第一次上线时间）
     */
    private LocalDateTime activeTime;
}
