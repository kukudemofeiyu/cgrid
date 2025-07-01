package com.things.cgomp.alarm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("device_alarm")
public class DeviceAlarmInfo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long deviceId;

    private Long portId;

    private Integer type;

    private String code;

    private String reason;

    private String detailInfo;

    private Date alarmTime;

    private Date recoveryTime;

    private Integer recoveryType;

    private Integer dealStatus;

    private Long dealBy;

    @TableField(exist = false)
    private String sn;

    private Date createTime;


}
