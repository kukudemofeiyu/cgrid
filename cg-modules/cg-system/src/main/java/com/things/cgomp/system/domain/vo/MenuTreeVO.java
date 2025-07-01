package com.things.cgomp.system.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单树结构实体类
 *
 * @author things
 */
@Data
@Accessors(chain = true)
public class MenuTreeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private Long menuId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 父菜单ID
     */
    private Long parentId;
    /**
     * 排序
     */
    private Integer orderNum;
    /**
     * 路由地址
     */
    private String path;
    /**
     * 是否为外链（1是 0否）
     */
    private Integer isFrame;
    /**
     * 类型
     * M目录 C菜单 F按钮
     */
    private String menuType;
    /**
     * 菜单状态
     * 1正常 0停用
     */
    private Integer status;
    /**
     * 显示状态
     * 1显示 0隐藏
     */
    private Integer visible;
    /**
     * 权限标识
     */
    private String perms;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 子菜单
     */
    private List<MenuTreeVO> children = new ArrayList<>();
}
