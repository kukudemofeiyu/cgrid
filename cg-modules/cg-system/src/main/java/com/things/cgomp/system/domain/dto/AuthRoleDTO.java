package com.things.cgomp.system.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author things
 * @date 2025/2/28
 */
@Data
public class AuthRoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "用户ID不能为空")
    private Long userId;
    /**
     * 角色ID
     */
    private Long[] roleIds = new Long[]{};
}
