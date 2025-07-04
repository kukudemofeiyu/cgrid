package com.things.cgomp.system.api.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.things.cgomp.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * 组织表 system_org
 * 
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("system_org")
public class SysOrg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 组织ID */
    @TableId
    private Long orgId;

    /** 父部门ID */
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    /** 部门名称 */
    @NotBlank(message = "部门名称不能为空")
    @Size(min = 0, max = 30, message = "部门名称长度不能超过30个字符")
    private String orgName;

    /** 显示顺序 */
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNum;

    /** 负责人 */
    private String leader;

    /** 联系电话 */
    @Size(min = 0, max = 11, message = "联系电话长度不能超过11个字符")
    private String phone;

    /** 邮箱 */
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    /**
     * 类型  0 平台管理员 1运营商
     */
    private Integer type;

    /** 部门状态:0正常,1停用 */
    private String status;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    @TableField(exist = false)
    /** 父部门名称 */
    private String parentName;

    @TableField(exist = false)
    /** 子部门 */
    private List<SysOrg> children = new ArrayList<SysOrg>();
}
