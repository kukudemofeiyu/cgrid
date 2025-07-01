package com.things.cgomp.system.api.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 金额变化记录表 system_amount_record
 *
 * @author things
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_amount_record")
public class SysAmountRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    /**
     * IC卡号
     */
    private String cardNo;
    /**
     * 记录绑定的用户ID
     */
    private Long bindUserId;
    /**
     * 记录操作的用户ID
     */
    private Long operateUserId;
    /**
     * 流水号
     * 充电消费时此处订单号
     */
    private String serialNumber;
    /**
     * 发生金额
     */
    private BigDecimal amount;
    /**
     * 变化前金额
     */
    private BigDecimal changeBefore;
    /**
     * 变化后金额
     */
    private BigDecimal changeAfter;
    /**
     * 已退款金额
     */
    private BigDecimal refundAmount;
    /**
     * 发生渠道
     * @see com.things.cgomp.common.record.enums.RecordChannel
     */
    private Integer channel;
    /**
     * 服务模块
     * @see com.things.cgomp.common.record.enums.RecordModule
     */
    private Integer module;
    /**
     * 类型
     * @see com.things.cgomp.common.record.enums.AmountRecordType
     */
    private Integer type;
    /**
     * 状态
     * @see com.things.cgomp.common.record.enums.RecordStatus
     */
    private Integer status;
    /**
     * 收支类型
     * @see com.things.cgomp.common.record.enums.IncomeExpenseType
     */
    private Integer recordType;
    /**
     * 用户类型
     * @see com.things.cgomp.common.core.enums.UserAccountType
     */
    private String userType;
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
     * 记录绑定者用户名
     */
    @TableField(exist = false)
    private String bindUser;
    /**
     * 记录绑定者手机号码
     */
    @TableField(exist = false)
    private String bindMobile;
    /**
     * 记录操作者用户名
     */
    @TableField(exist = false)
    private String operateMobile;
    /**
     * 可退款金额
     */
    @TableField(exist = false)
    private BigDecimal refundAvailable;
}
