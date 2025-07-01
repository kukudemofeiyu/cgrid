package com.things.cgomp.common.mq.message;

import com.things.cgomp.common.mq.common.AbstractBody;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class DeviceAlarmMsg extends AbstractBody {

    private Integer alarmType;

    private String alarmCode;
    private String alarmReason;

    private Long ts;

    /**
     * true-告警， false-恢复
     */
    private Boolean isAlarm;

}
