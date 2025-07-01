package com.things.cgomp.system.api.dto;

import com.things.cgomp.common.core.dto.PageDTO;
import lombok.Data;

@Data
public class AppRechargeRecordDTO extends PageDTO {
    private Long userId;
    /**
     * 开始日期
     */
    private String beginTime;
    /**
     * 结束日期
     */
    private String endTime;
}
