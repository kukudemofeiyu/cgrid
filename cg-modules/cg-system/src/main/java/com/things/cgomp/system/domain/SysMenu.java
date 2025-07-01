package com.things.cgomp.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.things.cgomp.common.core.web.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单权限表 system_menu
 * 
 * @author things
 */
@Data
@TableName("system_menu")
public class SysMenu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 菜单ID */
    @TableId
    private Long menuId;

    /** 菜单名称 */
    @NotBlank(message = "菜单名称不能为空")
    @Size(min = 0, max = 50, message = "菜单名称长度不能超过50个字符")
    private String menuName;

    /** 父菜单名称 */
    private String parentName;

    /** 父菜单ID */
    private Long parentId;

    /** 显示顺序 */
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNum;

    /** 路由地址 */
    @Size(min = 0, max = 200, message = "路由地址不能超过200个字符")
    private String path;

    /** 组件路径 */
    @Size(min = 0, max = 200, message = "组件路径不能超过255个字符")
    private String component;

    /** 路由参数 */
    private String query;

    /** 是否为外链（1是 0否） */
    private Integer isFrame;

    /** 是否缓存（1缓存 0不缓存） */
    private Integer isCache;

    /** 类型（M目录 C菜单 F按钮） */
    @NotBlank(message = "菜单类型不能为空")
    private String menuType;

    /** 显示状态（1显示 0隐藏） */
    private Integer visible;

    /** 菜单状态（1正常 0停用） */
    private Integer status;

    /** 权限字符串 */
    @Size(min = 0, max = 100, message = "权限标识长度不能超过100个字符")
    private String perms;

    /** 菜单图标 */
    private String icon;

    /** 子菜单 */
    private List<SysMenu> children = new ArrayList<SysMenu>();
}
