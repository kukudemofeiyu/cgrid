package com.things.cgomp.system.api.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.things.cgomp.common.core.annotation.Excel;
import com.things.cgomp.common.core.annotation.Excel.ColumnType;
import com.things.cgomp.common.core.annotation.Excel.Type;
import com.things.cgomp.common.core.annotation.Excels;
import com.things.cgomp.common.core.enums.OrgTypeEnum;
import com.things.cgomp.common.core.enums.UserAccountType;
import com.things.cgomp.common.core.web.domain.BaseEntity;
import com.things.cgomp.common.core.xss.Xss;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 用户对象 system_user
 * 
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("system_user")
public class SysUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @Excel(name = "用户序号", cellType = ColumnType.NUMERIC, prompt = "用户编号")
    @TableId
    private Long userId;

    /** 部门ID */
    @Excel(name = "组织编号", type = Type.IMPORT)
    private Long orgId;

    /** 用户账号 */
    @Excel(name = "登录名称")
    @Xss(message = "用户账号不能包含脚本字符")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    private String username;

    /** 用户真实名称 */
    @Excel(name = "用户真实名称")
    @Xss(message = "用户真实名称不能包含脚本字符")
    @NotBlank(message = "用户真实名称不能为空")
    @Size(min = 0, max = 30, message = "用户真实名称长度不能超过30个字符")
    private String realName;

    /** 用户类型 */
    @Excel(name = "用户类型")
    private Integer userType;

    /** 用户邮箱 */
    @Excel(name = "用户邮箱")
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    /** 手机号码 */
    @Excel(name = "手机号码")
    @NotBlank(message = "手机号码不能为空")
    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    private String mobile;

    /** 用户头像 */
    private String avatar;

    /** 密码 */
    private String password;

    /** 帐号状态（0正常 1停用） */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private Integer status;

    /** 删除标志（0代表存在 1代表删除） */
    private Integer delFlag;

    /**
     * 运营商权限维度
     * 1全部运营商 2部分运营商
     */
    private Integer operatorRange;
    /**
     * 站点权限维度
     * 1全部站点 2部分站点
     */
    private Integer siteRange;

    /** 最后登录IP */
    @Excel(name = "最后登录IP", type = Type.EXPORT)
    private String loginIp;

    /** 最后登录时间 */
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Date loginDate;

    /**
     * 密码更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date passwordUpdateTime;

    @TableField(exist = false)
    /** 部门对象 */
    @Excels({
            @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT),
            @Excel(name = "部门负责人", targetAttr = "leader", type = Type.EXPORT)
    })
    private SysOrg org;

    /**
     * 站点id列表
     */
    @TableField(exist = false)
    private List<Long> siteIds;
    /**
     * 运营商id列表
     */
    @TableField(exist = false)
    private List<Long> operatorIds;
    @TableField(exist = false)
    /** 角色对象 */
    private List<SysRole> roles;

    @TableField(exist = false)
    /** 角色组 */
    private Long[] roleIds;

    @TableField(exist = false)
    /** 岗位组 */
    private Long[] postIds;
    @TableField(exist = false)
    /** 角色ID */
    private Long roleId;
    @TableField(exist = false)
    private Long operatorId;
    /**
     * 组织名称
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private String orgName;
    /**
     * 类型 0系统管理员 1运营商
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Integer orgType;

    @JsonIgnore
    @TableField(exist = false)
    /** 用户账号类型 **/
    private UserAccountType userAccountType;

    @JsonIgnore
    @TableField(exist = false)
    private List<Integer> ignoreUserTypes;

    public SysUser(Long userId)
    {
        this.userId = userId;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

    public boolean isOperator(){
        return OrgTypeEnum.OPERATOR.getType().equals(this.orgType);
    }

    public void hidePwd(){
        this.password = null;
    }

    public Long buildRoleId() {
        if(CollectionUtils.isEmpty(roles)){
            return null;
        }
        SysRole role = roles.get(0);
        return role.getRoleId();
    }
}
