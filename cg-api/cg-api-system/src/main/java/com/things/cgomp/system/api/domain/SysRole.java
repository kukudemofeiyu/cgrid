package com.things.cgomp.system.api.domain;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.things.cgomp.common.core.annotation.Excel;
import com.things.cgomp.common.core.annotation.Excel.ColumnType;
import com.things.cgomp.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * 角色表 system_role
 * 
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_role")
public class SysRole extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 角色ID */
    @Excel(name = "角色序号", cellType = ColumnType.NUMERIC)
    @TableId
    private Long roleId;

    /** 角色名称 */
    @Excel(name = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    @Size(min = 0, max = 30, message = "角色名称长度不能超过30个字符")
    private String roleName;

    /** 角色权限 */
    @Excel(name = "角色权限")
    @Size(min = 0, max = 100, message = "权限字符长度不能超过100个字符")
    private String roleCode;

    /** 角色排序 */
    @Excel(name = "角色排序")
    private String roleSort;

    /** 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限） */
    @Excel(name = "数据范围", readConverterExp = "1=所有数据权限,2=自定义数据权限,3=本部门数据权限,4=本部门及以下数据权限,5=仅本人数据权限")
    private String dataScope;

    /** 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示） */
    private boolean menuCheckStrictly = false;

    /** 部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示 ） */
    private boolean orgCheckStrictly = false;

    /** 是否管理员角色（0 不是 1是） */
    @TableField(exist = false)
    private boolean adminRole;

    /** 角色状态（1正常 0停用） */
    @Excel(name = "角色状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /**
     * 进入首页展示菜单的id
     */
    private Long homeMenuId;

    /**
     * 半选菜单（前端使用）
     */
    private String halfMenuIdConfig;
    /**
     * 组织ID
     */
    private Long orgId;
    /**
     * 是否可选
     * 角色下拉框是否可选
     */
    private Boolean checkEnable;

    @TableField(exist = false)
    /** 用户是否存在此角色标识 默认不存在 */
    private boolean flag = false;

    @TableField(exist = false)
    /** 菜单组 */
    private Long[] menuIds;

    /**
     * 半选菜单（子菜单未全选）
     */
    @TableField(exist = false)
    private Long[] halfMenuIds;

    @TableField(exist = false)
    /** 组织组（数据权限） */
    private Long[] orgIds;

    /** 角色菜单权限 */
    @TableField(exist = false)
    private Set<String> permissions;

    private Integer type;

    private Integer orgType;

    public SysRole(Long roleId)
    {
        this.roleId = roleId;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.roleId);
    }

    public static boolean isAdmin(Long roleId)
    {
        return roleId != null && 1L == roleId;
    }

    public void setHalfMenuIdConfig() {
        String halfMenuIdConfig = buildHalfMenuIdConfig();
        setHalfMenuIdConfig(halfMenuIdConfig);
    }

    private String buildHalfMenuIdConfig() {
        if (halfMenuIds == null) {
            return null;
        }
        return JSON.toJSONString(halfMenuIds);
    }
}
