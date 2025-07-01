package com.things.cgomp.system.api.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AppRefundRecordVO {
    private Long id;
    /**
     * 发生金额
     */
    private BigDecimal amount;
    /**
     * 申请时间
     */
    private String eventTime;
    /**
     * 状态 0失败 1成功 2审核中
     */
    private Integer status;


}
