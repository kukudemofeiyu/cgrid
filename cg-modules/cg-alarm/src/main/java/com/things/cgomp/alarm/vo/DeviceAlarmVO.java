package com.things.cgomp.alarm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class DeviceAlarmVO implements Serializable {

    private Long alarmId;

    private String sn;
    private String portSn;

    private String alarmType;

    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ts;

    private Integer alarmStatus;



}
