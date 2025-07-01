package com.things.cgomp.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 注册用户充值记录返回对象
 *
 * @author things
 */
@Data
@Accessors(chain = true)
public class AppUserRechargeRecordVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * IC卡号
     */
    private String cardNo;
    /**
     * 充值唯一编码
     */
    private String serialNumber;
    /**
     * 发生金额
     */
    private BigDecimal amount;
    /**
     * 账户余额
     */
    private BigDecimal balance;

     /**
     * 可退款金额
     */
    private BigDecimal refundAvailable;
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
     * 备注
     */
    private String remark;
    /**
     * 发生时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eventTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 用户名
     */
    private String username;
    /**
     * 充值手机号
     */
    private String rechargeMobile;
    /**
     * 被充值手机号
     */
    private String beRechargeMobile;
}
