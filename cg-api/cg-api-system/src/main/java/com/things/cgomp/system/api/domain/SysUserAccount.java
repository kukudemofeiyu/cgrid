package com.things.cgomp.system.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.things.cgomp.common.core.enums.UserAccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户账号表 system_user_account
 *
 * @author things
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_user_account")
public class SysUserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    /**
     * 余额
     */
    private BigDecimal balance;
    /**
     * 账号类型
     * 01平台用户  02小程序用户
     * @see UserAccountType
     */
    @NotNull(message = "账户类型不能为空")
    private String type;
    /**
     * 版本号
     */
    @Version
    private Long version;
    /**
     * 账号状态
     * 1正常 0停用
     */
    private Integer status;
    /** 备注 */
    private String remark;
    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 新余额
     */
    @TableField(exist = false)
    private BigDecimal newBalance;
}
