package com.things.cgomp.system.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author things
 * @date 2025/3/5
 */
@Data
public class SysRoleSimpleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色编码
     */
    private String roleCode;
}
