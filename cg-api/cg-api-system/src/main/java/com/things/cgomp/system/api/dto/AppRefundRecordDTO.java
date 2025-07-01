package com.things.cgomp.system.api.dto;

import com.things.cgomp.common.core.dto.PageDTO;
import lombok.Data;

@Data
public class AppRefundRecordDTO extends PageDTO {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 提现状态(0失败 1成功 2审核中)
     */
    private Integer status;
    /**
     * 开始日期
     */
    private String beginTime;
    /**
     * 结束日期
     */
    private String endTime;
}
