package com.things.cgomp.device.api.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalTime;


@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
public class RuleTimeDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 规则Id
     */
    private Long ruleId;
    /**
     * 尖峰平谷类型（0-尖 1峰 2平 3谷）
     */
    private Integer type;

    /**
     * 时段开始时间
     */
    private String startTime;

    /**
     * 时段结束时间
     */
    private String endTime;

    public LocalTime buildStartTime(){
        if(startTime == null){
            return null;
        }

        return LocalTime.parse(startTime);
    }

    public LocalTime buildEndTime(){
        if(endTime == null){
            return null;
        }

        if("24:00".equals(endTime)){
            return LocalTime.parse("00:00");
        }

        return LocalTime.parse(endTime);
    }
}
