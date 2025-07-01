package com.things.cgomp.system.api.domain;

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
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 运营商表 system_operator
 *
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("system_operator")
public class SysOperator extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 运营商ID
     */
    @TableId
    private Long operatorId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 运营商名称
     */
    @NotBlank(message = "运营商名称不能为空")
    private String name;
    /**
     * 运营商地址
     */
    @NotBlank(message = "运营商地址不能为空")
    private String address;
    /**
     * 入驻日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date entryDate;
    /**
     * 运营商分成比例
     */
    @TableField(exist = false)
    private BigDecimal commissionPercent;
    /**
     * 平台分成比例
     */
    @TableField(exist = false)
    private BigDecimal platformPercent;
    /**
     * 分成类型
     * 1电费 2服务费 3电费+服务费
     */
    private Integer commissionType;
    /**
     * 运营商状态
     * 0正常 1停用
     * */
    private Integer status;
    /**
     * 删除标识
     * 0存在 1删除
     */
    private Integer delFlag;
    /**
     * 组织ID
     */
    private Long orgId;
    /**
     * 运营商用户名
     */
    @Xss(message = "用户账号不能包含脚本字符")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    @TableField(exist = false)
    private String username;
    /**
     * 运营商真实名称
     */
    @Xss(message = "用户真实名称不能包含脚本字符")
    @NotBlank(message = "用户真实名称不能为空")
    @Size(min = 0, max = 30, message = "用户真实名称长度不能超过30个字符")
    @TableField(exist = false)
    private String realName;
    /**
     * 运营商手机号码
     */
    @NotBlank(message = "手机号码不能为空")
    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    @TableField(exist = false)
    private String mobile;

    /**
     * 登录密码
     */
    @TableField(exist = false)
    private String password;

    /**
     * 累计收益
     */
    @TableField(exist = false)
    private BigDecimal totalIncome = BigDecimal.ZERO;
    /**
     * 账户余额
     */
    @TableField(exist = false)
    private BigDecimal accountBalance;
    /**
     * 设备数量
     */
    @TableField(exist = false)
    private Integer deviceCount = 0;
    /**
     * 站点数量
     */
    @TableField(exist = false)
    private Integer siteCount = 0;
    /**
     * 运营商角色ID集合
     */
    @TableField(exist = false)
    private Long[] roleIds;
}
