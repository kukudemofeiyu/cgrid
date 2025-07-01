package com.things.cgomp.system.domain.dto;

import com.things.cgomp.system.api.domain.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author things
 * @date 2025/2/27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdatePwdDTO extends SysUser {

    /** 旧密码 */
    private String oldPassword;
}
