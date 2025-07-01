package com.things.cgomp.alarm.dto;

import com.things.cgomp.common.core.web.domain.BaseEntity;
import lombok.Data;

@Data
public class DeviceAlarmDTO extends BaseEntity {
    /**
     * 运营商ID
     */
    private Long operatorId;

    private String siteName;

    /**
     * 充电桩编号
     */
    private String sn;



}
