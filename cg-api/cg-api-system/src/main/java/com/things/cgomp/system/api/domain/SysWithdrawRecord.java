package com.things.cgomp.system.api.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 提现记录表 system_withdraw_record
 *
 * @author things
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("system_withdraw_record")
public class SysWithdrawRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    /**
     * 申请用户ID
     * 运营商用户和小程序注册用户
     */
    private Long userId;
    /**
     * 运营商ID
     * 只有运营商提现时才有此值
     */
    private Long operatorId;
    /**
     * 发生金额
     */
    private BigDecimal amount;
    /**
     * 赠送金额
     */
    private BigDecimal giftAmount;
    /**
     * 服务模块
     * @see com.things.cgomp.common.record.enums.RecordModule
     */
    private Integer module;
    /**
     * 提现状态
     * @see com.things.cgomp.common.record.enums.RecordStatus
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eventTime;
    /**
     * 处理人
     */
    private Long handleBy;
    /**
     * 处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date handleTime;
}
