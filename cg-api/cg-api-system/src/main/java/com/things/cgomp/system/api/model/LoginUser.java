package com.things.cgomp.system.api.model;

import com.things.cgomp.system.api.domain.SysUser;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

/**
 * 用户信息
 *
 * @author things
 */
@Data
@Accessors(chain = true)
public class LoginUser implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 用户名id
     */
    private Long userid;
    /**
     * 租户id
     */
    private Long tenantId;
    /**
     * 运营商id
     */
    private Long operatorId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 角色列表
     */
    private Set<String> roles;

    /**
     * 用户信息
     */
    private SysUser sysUser;

    public Long buildRoleId() {
        if (sysUser == null) {
            return null;
        }
        return sysUser.buildRoleId();
    }
}
