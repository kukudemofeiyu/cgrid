package com.things.cgomp.system.domain.vo.appusergroup;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * app用户组映射表
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
@Getter
@Setter
@Accessors(chain = true)
public class AppUserGroupUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long groupId;

    private Long userId;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户昵称
     */
    private String nickName;
}
