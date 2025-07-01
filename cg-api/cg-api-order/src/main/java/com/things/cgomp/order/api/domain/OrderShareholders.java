package com.things.cgomp.order.api.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.things.cgomp.common.core.web.domain.BaseEntity;
import com.things.cgomp.common.core.xss.Xss;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分成者表 order_shareholders
 *
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("order_shareholders")
public class OrderShareholders extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 运营商ID
     */
    private Long operatorId;
    /**
     * 分成者地址
     */
    @NotBlank(message = "分成者地址不能为空")
    private String address;
    /**
     * 入驻日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "入驻日期不能为空")
    private Date entryDate;
    /**
     * 分成比例
     */
    @NotNull(message = "分成比例不能为空")
    @TableField(exist = false)
    private BigDecimal commissionPercent;
    /**
     * 分成者状态
     * 0启用 1停用
     */
    private Integer status;

    /**
     * 删除标识
     * 0存在 2删除
     */
    private Integer delFlag;

    /**
     * 分成者用户名
     */
    @Xss(message = "用户账号不能包含脚本字符")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    @TableField(exist = false)
    private String username;
    /**
     * 分成者真实名称
     */
    @Xss(message = "用户真实名称不能包含脚本字符")
    @NotBlank(message = "用户真实名称不能为空")
    @Size(min = 0, max = 30, message = "用户真实名称长度不能超过30个字符")
    @TableField(exist = false)
    private String realName;
    /**
     * 分成者手机号码
     */
    @NotBlank(message = "手机号码不能为空")
    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    @TableField(exist = false)
    private String mobile;
    /**
     * 分成者余额
     */
    @TableField(exist = false)
    private String balance;
    /**
     * 剩余分成比例（运营商）
     */
    @TableField(exist = false)
    private BigDecimal surplusRadio;
}
