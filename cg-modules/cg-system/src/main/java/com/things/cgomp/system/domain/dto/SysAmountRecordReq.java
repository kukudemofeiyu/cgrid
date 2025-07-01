package com.things.cgomp.system.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 * @author things
 */
@Data
@Accessors(chain = true)
public class SysAmountRecordReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    private Long id;
    /**
     * 关键字
     * 昵称/手机号
     */
    private String keyword;
    /**
     * 模块
     * @see com.things.cgomp.common.record.enums.RecordModule
     */
    private Integer module;
    /**
     * 记录类型
     * @see com.things.cgomp.common.record.enums.AmountRecordType
     */
    private Integer type;
    /**
     * 记录状态
     * @see com.things.cgomp.common.record.enums.RecordStatus
     */
    private Integer status;
    /**
     * 充值渠道
     * @see com.things.cgomp.common.record.enums.RecordChannel
     */
    private Integer channel;
    /**
     * 收支类型
     * @see com.things.cgomp.common.record.enums.IncomeExpenseType
     */
    private Integer recordType;
    /**
     * 开始日期
     */
    private String beginTime;
    /**
     * 结束日期
     */
    private String endTime;
}
