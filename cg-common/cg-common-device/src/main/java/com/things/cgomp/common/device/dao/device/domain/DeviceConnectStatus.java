package com.things.cgomp.common.device.dao.device.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备状态表 device_connect_status
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("device_connect_status")
public class DeviceConnectStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 设备编号
     */
    @TableId
    private Long deviceId;
    /**
     * 设备状态
     * 1在线 0离线
     */
    private Integer status;
    /**
     * 设备网关节点ID
     */
    private String nodeId;
    /**
     * 最后活跃时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validTime;
    /**
     * 会话开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sessionStartTime;
    /**
     * broker服务ID
     */
    private Integer brokerId;
}
