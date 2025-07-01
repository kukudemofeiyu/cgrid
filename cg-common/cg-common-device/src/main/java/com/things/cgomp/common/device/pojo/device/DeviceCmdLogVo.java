package com.things.cgomp.common.device.pojo.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Accessors(chain = true)
public class DeviceCmdLogVo {

    /**
     * 充电桩设备id
     */
    private Long deviceId;

    /**
     * 充电桩编号
     */
    private String sn;

    /**
     * 事件时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eventTs;

    /**
     * 设备指令 充电桩心跳包(0x0003)
     */
    private String cmd;

    /**
     * 1-上行 2-下行
     */
    private Integer upType;

    /**
     * 序列号
     */
    private Integer serialNo;

    /**
     * 消息体
     */
    private String content;

    /**
     * 描述
     */
    private String desc;

    private String operatorName;



}
