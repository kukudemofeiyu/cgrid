package com.things.cgomp.common.device.dao.device.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.things.cgomp.common.device.enums.PortStatusChangeFlag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 充电枪状态对象
 * @author things
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("device_port_status")
public class DevicePortStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 充电枪ID
     */
    @TableId
    @JsonIgnore
    private Long portId;
    /**
     * 枪状态
     * 0离线 1故障 2空闲 3充电
     */
    private Integer status;
    /**
     * 车VIN码
     */
    private String vin;
    /**
     * 订单号
     */
    private String orderSn;
    /**
     * 状态变化时间
     */
    private Long statusTime;
    /**
     * 是否插枪
     */
    private Boolean portInserted;
    /**
     * 插枪时间
     * 第一次插枪时间
     */
    private Long portInsertedTime;
    /**
     * 充电枪归位状态
     * 0未归位 1已归位 2未知
     */
    private Integer homeStatus;
    /**
     * 枪口业务状态
     * 0-离线 1-故障 2-空闲 3-充电 4-已插枪 5-占用
     */
    @JsonIgnore
    @TableField(exist = false)
    private Integer portStatus;
    /**
     * 枪口状态开始时间
     */
    @JsonIgnore
    @TableField(exist = false)
    private Long portStatusStartTime;
    /**
     * 枪口状态持续时间
     */
    @JsonIgnore
    @TableField(exist = false)
    private BigDecimal portStatusDuration;
    /**
     * 更新时间
     */
    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 备注
     */
    @JsonIgnore
    @TableField(exist = false)
    private String remark;
    /**
     * 变化标识
     */
    @JsonIgnore
    @TableField(exist = false)
    private PortStatusChangeFlag changeFlag;
    /**
     * 变化对象
     */
    @JsonIgnore
    @TableField(exist = false)
    private PortChangeValue changeValue;
}
